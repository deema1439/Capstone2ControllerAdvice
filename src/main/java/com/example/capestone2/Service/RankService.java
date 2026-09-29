package com.example.capestone2.Service;

import com.example.capestone2.Api.ApiException;
import com.example.capestone2.Model.Game;
import com.example.capestone2.Model.Rank;
import com.example.capestone2.Repository.GameRepository;
import com.example.capestone2.Repository.PlayerRepository;
import com.example.capestone2.Repository.RankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RankService {
  private final RankRepository rankRepository;
  private final GameRepository gameRepository;
  private final PlayerRepository playerRepository;


public List<Rank>getRanks(){
  return rankRepository.findAll();
}

public void addRank(Rank rank){
  Game existingGame=gameRepository.findGameById(rank.getGameId());
  if(existingGame==null){
   throw new ApiException("game not found"); //"game not found"
  }
  rankRepository.save(rank);
}

public void updateRank(Integer id,Rank rank){
  Rank oldRank=rankRepository.findRankById(id);
  Game existingGame=gameRepository.findGameById(rank.getGameId());
  if(oldRank==null){
   throw new ApiException("rank not found");
  }
  if(existingGame==null){
   throw new ApiException("game id not found");
  }
  if (!oldRank.getGameId().equals(rank.getGameId())) {
    if (playerRepository.existsByRankId(id)) {
      throw new ApiException(" rank is in use, cannot change its game");
    }
  }
  oldRank.setName(rank.getName());
  oldRank.setTierOrder(rank.getTierOrder());
  oldRank.setGameId(rank.getGameId());
  rankRepository.save(oldRank);
}


public void deleteRank(Integer id){
  Rank existingRank=rankRepository.findRankById(id);
  if(existingRank==null){
    throw new ApiException("rank not found"); //"rank not found"
  }
  if (playerRepository.existsByRankId(id)) {
   throw new ApiException("rank is in use");
  }
  rankRepository.delete(existingRank);
}




















}
