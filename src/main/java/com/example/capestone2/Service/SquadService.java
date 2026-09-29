package com.example.capestone2.Service;

import com.example.capestone2.Api.ApiException;
import com.example.capestone2.Model.*;
import com.example.capestone2.Repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SquadService {
 private final SquadRepository squadRepository;
 private final GameRepository gameRepository;
 private final PlayerRepository playerRepository;
 private final SquadMembersRepository squadMembersRepository;
 private final EmailService emailService;
 private final RankRepository rankRepository;
 private final RoleRepository roleRepository;
 private final AiService aiService;


 public List<Squad>getSquads(){
  return squadRepository.findAll();
 }



 public void addSquad(Squad squad){
  Game existingGame=gameRepository.findGameById(squad.getGameId());
  Player existingPlayer=playerRepository.findPlayerById(squad.getOwnerId());
  if(existingGame==null){
  throw new ApiException("game Not Found");
  }
  if(existingPlayer==null){
   throw new ApiException("Player Not Found");
  }
  squad.setStatus("OPEN");
  squad.setCreatedAt(LocalDateTime.now());
  squadRepository.save(squad);
 }

 public void closeSquad(Integer id, Integer ownerId) {
  Squad oldSquad = squadRepository.findSquadById(id);
  if (oldSquad == null) {
   throw new ApiException("squad not found");
  }
  if (!oldSquad.getOwnerId().equals(ownerId)) {
  throw new ApiException("only the owner can close the squad");
  }
  oldSquad.setStatus("Closed");
  squadRepository.save(oldSquad);
 }

 public void openSquad(Integer id,Integer ownerId){
 Squad oldsquad =squadRepository.findSquadById(id);
 if(oldsquad==null){
  throw new ApiException("squad Not Found");
 }
 if(!oldsquad.getOwnerId().equals(ownerId)){
  throw new ApiException("only the owner can open the squad");
 }
 if(oldsquad.getStatus().equals("OPEN")){
  throw new ApiException("squad is already open");
 }
 oldsquad.setStatus("OPEN");
 squadRepository.save(oldsquad);
 }

 @Transactional
 public void deleteSquad(Integer id, Integer ownerId) {
  Squad existingSquad = squadRepository.findSquadById(id);
  if (existingSquad == null) {
   throw new ApiException("squad not found");
  }
  if (!existingSquad.getOwnerId().equals(ownerId)) {
   throw new ApiException("only the owner can delete the squad");
  }
  squadMembersRepository.deleteBySquadId(id);   // ← يمسح سجلات العضوية المرتبطه أولاً
  squadRepository.delete(existingSquad);
 }


 public void kickPlayer(Integer squadId, Integer ownerId, Integer playerId){
  Squad s=squadRepository.findSquadById(squadId);

  if(s==null){
   throw new ApiException("Squad Not Found"); //Squad Not Found
  }
  if(!s.getOwnerId().equals(ownerId)){
   throw new ApiException("only the owner can kick players");//only the owner can kick players
  }
  if(ownerId.equals(playerId)){
   throw new ApiException("owner can't kick himself"); // owner can't kick himself
  }
  SquadMembers member =squadMembersRepository.findSquadMembersBySquadIdAndPlayerId(squadId,playerId);
  if(member==null){
   throw new ApiException("player is not in this squad"); // player is not in this squad
  }
  squadMembersRepository.delete(member);
  Player player = playerRepository.findPlayerById(playerId);
  emailService.sendEmail(player.getEmail(), "Removed from squad",
          "Hi " + player.getName() + ", you have been removed from " + s.getName() + ".");
 }



 public String getSquadMembers(Integer squadId, Integer ownerId){
  Squad s = squadRepository.findSquadById(squadId);
  if(s == null){
   throw new ApiException( "Squad Not Found");
  }
  if(!s.getOwnerId().equals(ownerId)){
   throw new ApiException("Only Owner Can See members");
  }
  Game game = gameRepository.findGameById(s.getGameId());
  List<SquadMembers> squadMembers = squadMembersRepository.findAllBySquadId(squadId);
  Player owner = playerRepository.findPlayerById(s.getOwnerId());
  String result = "Squad: " + s.getName() + " | Game: " + game.getName()
          + " | Members count: " + (squadMembers.size() + 1) + " | "
          + owner.getName() + " (" + getRankName(owner) + " - Owner), ";

  for(SquadMembers m : squadMembers){
   Player p = playerRepository.findPlayerById(m.getPlayerId());
   Role role = roleRepository.findRoleById(m.getRoleId());
   result += p.getName() + " (" + getRankName(p) + " - " + role.getName() + "), ";
  }
  return result;
 }


public void playerLeaveSquad(Integer squadId,Integer playerId){
  Squad s=squadRepository.findSquadById(squadId);
  if(s==null){
  throw new ApiException("Squad Not Found");
  }
  if(s.getOwnerId().equals(playerId)){
  throw new ApiException("owner can't leave, he must delete the squad");//owner can't leave, he must delete the squad
  }
  SquadMembers member=squadMembersRepository.findSquadMembersBySquadIdAndPlayerId(squadId,playerId);
  if(member==null){
   throw new ApiException("player is not in this squad");
  }
  squadMembersRepository.delete(member);
 Player player = playerRepository.findPlayerById(playerId);
 Player owner = playerRepository.findPlayerById(s.getOwnerId());
 emailService.sendEmail(owner.getEmail(), "Player left your squad",
         "Hi " + owner.getName() + ", " + player.getName() + " has left " + s.getName() + ".");
}

 public String describeSquad(Integer squadId){
  Squad s = squadRepository.findSquadById(squadId);
  if(s == null){
  throw new ApiException("Squad Not Found");
  }
  Game game = gameRepository.findGameById(s.getGameId());
  List<SquadMembers> squadMembers = squadMembersRepository.findAllBySquadId(squadId);
  String roles = "";
  for(SquadMembers m : squadMembers){
   Role role = roleRepository.findRoleById(m.getRoleId());
   roles += role.getName() + ", ";
  }

  if(roles.isEmpty()){
   roles = "no members yet";
  }

  String prompt = "Write a short catchy description (2 sentences) for a gaming squad. "
          + "Squad name: " + s.getName()
          + ". Game: " + game.getName()
          + ". Members count: " + (squadMembers.size()+1) + " out of " + game.getMaxPlayer()
          + ". Current roles: " + roles
          + ". Status: " + s.getStatus()
          + ". Mention which roles they still need if the squad is not full.";

  return aiService.ask(prompt);
 }




 public void transferOwnerShip(Integer squadId, Integer ownerId, Integer newOwnerId) {
  Squad s = squadRepository.findSquadById(squadId);
  if (s == null) {
  throw new ApiException("squad not found");// squad not found
  }
  if (!s.getOwnerId().equals(ownerId)) {
   throw new ApiException("only the owner can transfer ownership");
  }
  if (ownerId.equals(newOwnerId)) {
   throw new ApiException("you are already the owner");//
  }
  SquadMembers newOwnerMember = squadMembersRepository.findSquadMembersBySquadIdAndPlayerId(squadId, newOwnerId);
  if (newOwnerMember == null) {
   throw new ApiException(" new owner must be a member of the squad");
  }
  squadMembersRepository.delete(newOwnerMember);
  s.setOwnerId(newOwnerId);
  squadRepository.save(s);
  Player newOwner = playerRepository.findPlayerById(newOwnerId);
  emailService.sendEmail(newOwner.getEmail(), "You are the new squad owner",
          "Hi " + newOwner.getName() + ", you are now the owner of " + s.getName() + ".");

 }


 public List<Squad> getPlayerSquads(Integer playerId){
  Player player = playerRepository.findPlayerById(playerId);
  if(player == null){
   throw new ApiException(" player not found");
  }
  return squadRepository.findSquadsByPlayerId(playerId);
 }

 private String getRankName(Player player){
  Rank rank=rankRepository.findRankById(player.getRankId());
  if(rank==null){
 throw new ApiException(" player not found");
  }
    return rank.getName();
 }


 private String getSquadInfo(Squad s){
  Player owner = playerRepository.findPlayerById(s.getOwnerId());
  String info = "the Squad name is: " + s.getName()
          + " The owner of the Squad is: " + owner.getName()
          + " the rank of the Owner: " + getRankName(owner)
          + " the rating: " + owner.getRating() + "\n";

  List<SquadMembers> members = squadMembersRepository.findAllBySquadId(s.getId());
  for (SquadMembers m : members){
   Player p = playerRepository.findPlayerById(m.getPlayerId());
   Role r = roleRepository.findRoleById(m.getRoleId());
   info += "- " + p.getName() + ": rank " + getRankName(p) + ", role " + r.getName()
           + ", rating " + p.getRating() + "\n";
  }
  return info;
 }


 public String getSquadCompatibility(Integer squadId){
  Squad s=squadRepository.findSquadById(squadId);
  if(s==null){
   throw new ApiException("Squad Not Found");
  }
  if(squadMembersRepository.countBySquadId(squadId)==0){
   throw new ApiException("Squad has No members");
  }
  Game game=gameRepository.findGameById(s.getGameId());
  String prompt = "You are a gaming team analyst. Rate how compatible this " + game.getName()
          + " squad is as a team. Check if their ranks are close, if the roles are balanced, and their ratings.\n"
          + getSquadInfo(s)
          + "Reply in English in this exact format only:\n"
          + "Compatibility: X%\n"
          + "Reason: one short sentence";
          return aiService.ask(prompt);
 }


 public String getPlayerCompatibility(Integer squadId,Integer playerId){
  Squad s=squadRepository.findSquadById(squadId);
  if(s==null){
   throw new ApiException("there is no Squad");
  }
  Player p=playerRepository.findPlayerById(playerId);
  if(p==null){
  throw new ApiException("there is no player");
  }
  if(squadMembersRepository.existsBySquadIdAndPlayerId(squadId,playerId)||s.getOwnerId().equals(playerId)){
   throw new ApiException("Player Already In Squad");
  }
  Game game=gameRepository.findGameById(s.getGameId());
  String prompt = "You are a gaming team analyst. Rate how well this player would fit into this "
          + game.getName() + " squad, based on rank closeness, roles the squad still needs, and rating.\n"
          + "Player: " + p.getName() + ", rank " + getRankName(p) + ", rating " + p.getRating() + "\n"
          + "Squad members: (" + (squadMembersRepository.countBySquadId(squadId)+1)
          + " out of " + game.getMaxPlayer() + ")\n"
          + getSquadInfo(s)
          + "Reply in English in this exact format only:\n"
          + "Compatibility: X%\n"
          + "Reason: one short sentence";
  return aiService.ask(prompt);
 }







































}
