package com.example.capestone2.Service;

import com.example.capestone2.Api.ApiException;
import com.example.capestone2.Model.Game;
import com.example.capestone2.Repository.GameRepository;
import com.example.capestone2.Repository.SquadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {
 private final GameRepository gameRepository;
 private final SquadRepository squadRepository;

 public List<Game>getGames(){
     return gameRepository.findAll();
 }

 public void addGame(Game game){
     gameRepository.save(game);
 }

 public void updateGame(Integer id,Game game){
     Game oldGame=gameRepository.findGameById(id);
     if(oldGame==null){
         throw new ApiException("game not found");
     }
     oldGame.setGenre(game.getGenre());
     oldGame.setMaxPlayer(game.getMaxPlayer());
     oldGame.setName(game.getName());
     gameRepository.save(oldGame);
 }


 public void deleteGame(Integer id){
     Game existingGame=gameRepository.findGameById(id);
     if(existingGame==null){
        throw new ApiException("game not found");//game not found
     }
     if (squadRepository.existsByGameId(id)) {
        throw new ApiException("game has squads, cannot delete"); // game has squads, cannot delete
     }
     gameRepository.delete(existingGame);
 }








































}
