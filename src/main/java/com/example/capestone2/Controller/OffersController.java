package com.example.capestone2.Controller;

import com.example.capestone2.Api.ApiRes;
import com.example.capestone2.Model.Offers;
import com.example.capestone2.Service.OffersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/offers")
@RequiredArgsConstructor
public class OffersController {
    private final OffersService offersService;

    @GetMapping("/get")
    public ResponseEntity<?> getOffers(){
        return ResponseEntity.status(200).body(offersService.getOffers());
    }


    @PostMapping("/make/{ownerId}")
    public ResponseEntity<?> makeOffer(@PathVariable Integer ownerId, @RequestBody @Valid Offers offers){
        offersService.makeOffer(offers, ownerId);
        return ResponseEntity.status(200).body(new ApiRes("offer sent"));

    }

    @PutMapping("/accept/{offerId}/{playerId}")
    public ResponseEntity<?> acceptOffer(@PathVariable Integer offerId, @PathVariable Integer playerId){
       offersService.acceptOffer(offerId, playerId);
       return ResponseEntity.status(200).body(new ApiRes("offer accepted"));
    }

    @PutMapping("/reject/{offerId}/{playerId}")
    public ResponseEntity<?> rejectOffer(@PathVariable Integer offerId, @PathVariable Integer playerId){
        offersService.rejectOffer(offerId, playerId);
        return ResponseEntity.status(200).body(new ApiRes("offer rejected"));
    }

    @DeleteMapping("/delete/{offerId}/{ownerId}")
    public ResponseEntity<?> deleteOffer(@PathVariable Integer offerId, @PathVariable Integer ownerId){
        offersService.deleteOffer(offerId, ownerId);
        return ResponseEntity.status(200).body(new ApiRes("offer withdrawn"));

    }

    @GetMapping("/pending/{playerId}")
    public ResponseEntity<?> getPendingOffers(@PathVariable Integer playerId){
        List<Offers> offers = offersService.getPendingOffers(playerId);
        return ResponseEntity.status(200).body(offers);
    }


}
