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
public class OffersService {
    private final OffersRepository offersRepository;
    private final SquadRepository squadRepository;
    private final PlayerRepository playerRepository;
    private final RoleRepository roleRepository;
    private final SquadMembersRepository squadMembersRepository;
    private final GameRepository gameRepository;
    private final EmailService emailService;

    public List<Offers> getOffers() {
        return offersRepository.findAll();
    }


    public void makeOffer(Offers offers, Integer ownerId) {
        //اتآكد ان الريكوست ايدي هو نفسه اونر القروب ضروري
        Squad s = squadRepository.findSquadById(offers.getSquadId());
        if (s == null) {
            throw new ApiException("squad not found");
        }
        if (!s.getOwnerId().equals(ownerId)) {
            throw new ApiException("only the squad owner can make offers");
        }
        Player p = playerRepository.findPlayerById(offers.getPlayerId());
        if (p == null) {
            throw new ApiException("player not found");
        }
        Role r = roleRepository.findRoleById(offers.getRoleId());
        if (r == null) {
            throw new ApiException("role not found");
        }
        if (squadMembersRepository.existsBySquadIdAndPlayerId(offers.getSquadId(), offers.getPlayerId())) {
            throw new ApiException("player already in squad");
        }
        if (offersRepository.existsBySquadIdAndPlayerIdAndStatus(offers.getSquadId(), offers.getPlayerId(), "PENDING")) {
            throw new ApiException("offer already sent and still pending");
        }
        if (s.getStatus().equals("Closed")) {
            throw new ApiException("squad is closed");
        }

        if (!r.getGameId().equals(s.getGameId())) {
            throw new ApiException("role does not belong to this squad's game");
        }

        offers.setStatus("PENDING");
        offers.setCreatedAt(LocalDateTime.now());
        offersRepository.save(offers);
        Player owner = playerRepository.findPlayerById(s.getOwnerId());
        emailService.sendEmail(p.getEmail(), "New squad offer",
                "Hi " + p.getName() + ", " + owner.getName() + " invited you to join " + s.getName() + ".");

    }


    public void acceptOffer(Integer offerId, Integer playerId) {
        Offers offers = offersRepository.findOffersById(offerId);
        if (offers == null) {
            throw new ApiException("no offers");
        }

        if (!offers.getPlayerId().equals(playerId)) {
            throw new ApiException("this offer not send to this player");
        }

        if (!offers.getStatus().equals("PENDING")) {
           throw new ApiException("already responded to");
        }
        Squad squad = squadRepository.findSquadById(offers.getSquadId());
        if (squad == null) {
            throw new ApiException("squad not found");
        }
        Game game = gameRepository.findGameById(squad.getGameId());
        if (game == null) {
            throw new ApiException("game not found"); // game not found
        }
        long currentMembers = squadMembersRepository.countBySquadId(squad.getId());
        if (currentMembers+1 >= game.getMaxPlayer()) {
           throw new ApiException("squad is full");
        }

        if (squad.getStatus().equals("Closed")) {
            throw new ApiException("squad is closed");
        }
        if (squadMembersRepository.existsBySquadIdAndPlayerId(squad.getId(), playerId)) {
            throw new ApiException("player already in squad");
        }

        offers.setStatus("ACCEPTED");
        offers.setRespondedAt(LocalDateTime.now());
        offersRepository.save(offers);
        SquadMembers member = new SquadMembers();
        member.setSquadId(offers.getSquadId());
        member.setPlayerId(offers.getPlayerId());
        member.setRoleId(offers.getRoleId());
        squadMembersRepository.save(member);
        Player owner = playerRepository.findPlayerById(squad.getOwnerId());
        Player player = playerRepository.findPlayerById(offers.getPlayerId());
        emailService.sendEmail(owner.getEmail(), "Offer accepted",
                "Hi " + owner.getName() + ", " + player.getName() + " accepted your offer and joined " + squad.getName() + ".");

    }

    // اللاعب يرفض الدعوة
    public void rejectOffer(Integer offerId, Integer playerId) {
        Offers existingOffer = offersRepository.findOffersById(offerId);
        if (existingOffer == null) {
            throw new ApiException("offer not found"); // offer not found
        }
        if (!existingOffer.getPlayerId().equals(playerId)) {
           throw new ApiException("this offer was not sent to this player");
        }
        if (!existingOffer.getStatus().equals("PENDING")) {
            throw new ApiException("already responded to");
        }
        existingOffer.setStatus("REJECTED");
        existingOffer.setRespondedAt(LocalDateTime.now());
        offersRepository.save(existingOffer);
        Squad squad = squadRepository.findSquadById(existingOffer.getSquadId());
        Player owner = playerRepository.findPlayerById(squad.getOwnerId());
        Player player = playerRepository.findPlayerById(existingOffer.getPlayerId());
        emailService.sendEmail(owner.getEmail(), "Offer rejected",
                "Hi " + owner.getName() + ", " + player.getName() + " declined your offer to join " + squad.getName() + ".");
    }


    // owner يسحب دعوة معلقة لسا ما تم الرد عليها
    public void deleteOffer(Integer offerId, Integer ownerId) {
        Offers existingOffer = offersRepository.findOffersById(offerId);
        if (existingOffer == null) {
          throw new ApiException(" offer not found");
        }
        Squad squad = squadRepository.findSquadById(existingOffer.getSquadId());
        if (squad == null) {
            throw new ApiException("squad not found");
        }
        if (!squad.getOwnerId().equals(ownerId)) {
            throw new ApiException("only the squad owner can withdraw it");
        }
        if (!existingOffer.getStatus().equals("PENDING")) {
            throw new ApiException("already responded to,cannot withdraw");
        }
        offersRepository.delete(existingOffer);
    }


    public List<Offers> getPendingOffers(Integer playerId) {
    Player p=playerRepository.findPlayerById(playerId);
    if(p==null){
       throw new ApiException("player Not Found");
    }
    return offersRepository.findAllByPlayerIdAndStatus(playerId,"PENDING");
    }


}










