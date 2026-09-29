package com.example.capestone2.Controller;

import com.example.capestone2.Api.ApiRes;
import com.example.capestone2.Model.Game;
import com.example.capestone2.Service.GameService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping("/get")
    public ResponseEntity<?>getGames(){
        List<Game>getGames=gameService.getGames();
        return ResponseEntity.status(200).body(getGames);
    }


    @PostMapping("/add")
    public ResponseEntity<?> addGame(@RequestBody @Valid Game game){
        gameService.addGame(game);
        return ResponseEntity.status(200).body(new ApiRes("game added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateGame(@PathVariable Integer id, @RequestBody @Valid Game game){
        gameService.updateGame(id, game);
        return ResponseEntity.status(200).body(new ApiRes("game updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>deleteGame(@PathVariable Integer id){
        gameService.deleteGame(id);
        return ResponseEntity.status(200).body(new ApiRes("game deleted"));

    }








}
