package com.example.capestone2.Service;

import com.example.capestone2.Api.ApiException;
import com.example.capestone2.Model.*;
import com.example.capestone2.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestsService {
    private final RequestsRepository requestsRepository;
    private final PlayerRepository playerRepository;
    private final SquadRepository squadRepository;
    private final RoleRepository roleRepository;
    private final SquadMembersRepository squadMembersRepository;
    private final GameRepository gameRepository;
    private final EmailService emailService;
    private final WhatsappService whatsappService;

    public List<Requests>getRequests(){
        return requestsRepository.findAll();
       }


    public void MakeRequest(Requests requests){
        Player player=playerRepository.findPlayerById(requests.getPlayerId());
        Squad squad=squadRepository.findSquadById(requests.getSquadId());
        Role role=roleRepository.findRoleById(requests.getRoleId());
        boolean s=squadMembersRepository.existsBySquadIdAndPlayerId(requests.getSquadId(),requests.getPlayerId());
        boolean hasPending =requestsRepository.existsBySquadIdAndPlayerIdAndStatus(requests.getSquadId(),requests.getPlayerId(),"PENDING");
        if(player==null){
          throw new ApiException("player Not Found");
        }
        if(squad==null){
            throw new ApiException("Squad Not Found");
        }
        if(role==null){
            throw new ApiException("Role Not Found");
        }
        if(!role.getGameId().equals(squad.getGameId())){
            throw new ApiException("role does not belong to this squad's game");
        }
        if(s){
            throw new ApiException("player is already in squad");
        }
        if(hasPending){
            throw new ApiException("player is still pending");
        }
        requests.setStatus("PENDING");
        requests.setCreatedAt(LocalDateTime.now());
        requestsRepository.save(requests);
         Player owner=playerRepository.findPlayerById(squad.getOwnerId());
         emailService.sendEmail(owner.getEmail(),"New join request", "Hi " + owner.getName() + ", " + player.getName() + " wants to join " + squad.getName() + ".");
    }



    public void acceptRequest(Integer requestId,Integer ownerId){
      Requests request=requestsRepository.findRequestsById(requestId);
      if(request==null){
        throw new ApiException("requst not found");
      }
      Squad squad=squadRepository.findSquadById(request.getSquadId());
      if(squad==null){
          throw new ApiException("squad Not Found");
      }
       if(!squad.getOwnerId().equals(ownerId)){
           throw new ApiException("only the owner can accept");
       }
       if(squad.getStatus().equals("Closed")){
        throw new ApiException("Squad is Closed");
       }
       if(!request.getStatus().equals("PENDING")){
           throw new ApiException("Owner has already response");
       }

       long currentMembers = squadMembersRepository.countBySquadId(squad.getId());
       Game game=gameRepository.findGameById(squad.getGameId());
        if(game == null){
            throw new ApiException("game not found");
        }

        if(currentMembers + 1 >=game.getMaxPlayer()){
               throw new ApiException("game is full");
           }

        if(squadMembersRepository.existsBySquadIdAndPlayerId(squad.getId(), request.getPlayerId())){
            throw new ApiException("player already in squad");
        }

       request.setStatus("ACCEPTED");
        request.setRespondedAt(LocalDateTime.now());
       requestsRepository.save(request);
       SquadMembers s=new SquadMembers();
       s.setSquadId(request.getSquadId());
       s.setPlayerId(request.getPlayerId());
        s.setRoleId(request.getRoleId());
        squadMembersRepository.save(s);
        Player player=playerRepository.findPlayerById(request.getPlayerId());
        emailService.sendEmail(player.getEmail(),"Request accepted ","Hi "+player.getName()+", your request to join " +squad.getName()+" squad has been accepted!");
        whatsappService.sendMessage(player.getPhone(),
                "Hi " + player.getName() + ", your request to join " + squad.getName() + " has been accepted!");

    }


    public void deleteRequest(Integer requestId, Integer playerId) {
        Requests existingRequest = requestsRepository.findRequestsById(requestId);
        if (existingRequest == null) {
          throw new ApiException("request not found");
        }
        if (!existingRequest.getPlayerId().equals(playerId)) {
            throw new ApiException("only the player who sent it can withdraw it");
        }
        if (!existingRequest.getStatus().equals("PENDING")) {
            throw new ApiException("already responded to cannot withdraw");
        }
        requestsRepository.delete(existingRequest);
    }

    public void rejectRequest(Integer requestId, Integer ownerId){
        Requests request = requestsRepository.findRequestsById(requestId);
        if(request == null){
           throw new ApiException("request not found");
        }
        Squad squad = squadRepository.findSquadById(request.getSquadId());
        if(squad == null){
            throw new ApiException("squad not found");
        }
        if(!squad.getOwnerId().equals(ownerId)){
            throw new ApiException("only the owner can reject");
        }
        if(!request.getStatus().equals("PENDING")){
            throw new ApiException("already responded to");
        }
        request.setStatus("REJECTED");
        request.setRespondedAt(LocalDateTime.now());
        requestsRepository.save(request);
        Player player=playerRepository.findPlayerById(request.getPlayerId());
        emailService.sendEmail(player.getEmail(),"Rejected Request","Hi "+player.getName()+", your request to join " +squad.getName()+" squad has been Rejected!");
    }


    public List<Requests>getPendingRequests(Integer squadId,Integer ownerId){
        Squad s=squadRepository.findSquadById(squadId);
        if(s==null){
          throw new ApiException("squad not found");
        }
        if(!s.getOwnerId().equals(ownerId)){
            throw new ApiException("only the owner can see requests");
        }
        return requestsRepository.findAllBySquadIdAndStatus(squadId,"PENDING");
    }























}
