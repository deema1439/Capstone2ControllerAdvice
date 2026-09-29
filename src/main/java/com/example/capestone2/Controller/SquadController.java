package com.example.capestone2.Controller;

import com.example.capestone2.Api.ApiRes;
import com.example.capestone2.Model.Squad;
import com.example.capestone2.Service.SquadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/squad")
@RequiredArgsConstructor
public class SquadController {
    private final SquadService squadService;

    @GetMapping("/get")
    public ResponseEntity<?> getSquads(){
        return ResponseEntity.status(200).body(squadService.getSquads());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addSquad(@RequestBody @Valid Squad squad){
        squadService.addSquad(squad);
       return ResponseEntity.status(200).body(new ApiRes("squad added"));
    }

    @PutMapping("/close/{id}/{ownerId}")
    public ResponseEntity<?> closeSquad(@PathVariable Integer id, @PathVariable Integer ownerId){
        squadService.closeSquad(id, ownerId);
        return ResponseEntity.status(200).body(new ApiRes("squad closed"));

    }

    @PutMapping("/open/{id}/{ownerId}")
    public ResponseEntity<?> openSquad(@PathVariable Integer id, @PathVariable Integer ownerId){
         squadService.openSquad(id, ownerId);
         return ResponseEntity.status(200).body(new ApiRes("squad opened"));

    }


    @DeleteMapping("/delete/{id}/{ownerId}")
    public ResponseEntity<?> deleteSquad(@PathVariable Integer id, @PathVariable Integer ownerId){
         squadService.deleteSquad(id, ownerId);
         return ResponseEntity.status(200).body(new ApiRes("squad deleted"));

    }


    @DeleteMapping("/kick/{squadId}/{ownerId}/{playerId}")
    public ResponseEntity<?> kickPlayer(@PathVariable Integer squadId, @PathVariable Integer ownerId, @PathVariable Integer playerId){
         squadService.kickPlayer(squadId, ownerId, playerId);
         return ResponseEntity.status(200).body(new ApiRes("player kicked"));
    }


    @GetMapping("/members/{squadId}/{ownerId}")
    public ResponseEntity<?> getSquadMembers(@PathVariable Integer squadId, @PathVariable Integer ownerId){
        String details = squadService.getSquadMembers(squadId, ownerId);
        return ResponseEntity.status(200).body(new ApiRes(details));
    }


    @DeleteMapping("/leave/{squadId}/{playerId}")
    public ResponseEntity<?> leaveSquad(@PathVariable Integer squadId, @PathVariable Integer playerId){
         squadService.playerLeaveSquad(squadId, playerId);
         return ResponseEntity.status(200).body(new ApiRes("you left the squad"));

    }




    @GetMapping("/describe/{squadId}")
    public ResponseEntity<?> describeSquad(@PathVariable Integer squadId){
        String description = squadService.describeSquad(squadId);
        return ResponseEntity.status(200).body(new ApiRes(description));
    }


    @PutMapping("/transfer/{squadId}/{ownerId}/{newOwnerId}")
    public ResponseEntity<?> transferOwnerShip(@PathVariable Integer squadId, @PathVariable Integer ownerId, @PathVariable Integer newOwnerId){
         squadService.transferOwnerShip(squadId, ownerId, newOwnerId);
         return ResponseEntity.status(200).body(new ApiRes("ownership transferred"));

    }


    @GetMapping("/player-squads/{playerId}")
    public ResponseEntity<?> getPlayerSquads(@PathVariable Integer playerId){
        List<Squad> squads = squadService.getPlayerSquads(playerId);
        return ResponseEntity.status(200).body(squads);
    }

    @GetMapping("/compatibility/{squadId}")
    public ResponseEntity<?> getSquadCompatibility(@PathVariable Integer squadId){
        String result = squadService.getSquadCompatibility(squadId);
        return ResponseEntity.status(200).body(new ApiRes(result));
    }

    @GetMapping("/compatibility/{squadId}/{playerId}")
    public ResponseEntity<?> getPlayerCompatibility(@PathVariable Integer squadId, @PathVariable Integer playerId){
        String result = squadService.getPlayerCompatibility(squadId, playerId);
        return ResponseEntity.status(200).body(new ApiRes(result));
    }



















}
