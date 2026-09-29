package com.example.capestone2.Controller;

import com.example.capestone2.Api.ApiRes;
import com.example.capestone2.Model.Requests;
import com.example.capestone2.Service.RequestsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/requests")
@RequiredArgsConstructor
public class RequestsController {
    private final RequestsService requestsService;

    @GetMapping("/get")
    public ResponseEntity<?> getRequests(){
        return ResponseEntity.status(200).body(requestsService.getRequests());
    }


    @PostMapping("/make")
    public ResponseEntity<?> makeRequest(@RequestBody @Valid Requests requests){
        requestsService.MakeRequest(requests);
        return ResponseEntity.status(200).body(new ApiRes("request sent"));
    }


    @PutMapping("/accept/{requestId}/{ownerId}")
    public ResponseEntity<?> acceptRequest(@PathVariable Integer requestId, @PathVariable Integer ownerId){
       requestsService.acceptRequest(requestId, ownerId);
       return ResponseEntity.status(200).body(new ApiRes("request accepted"));
    }

    @PutMapping("/reject/{requestId}/{ownerId}")
    public ResponseEntity<?> rejectRequest(@PathVariable Integer requestId, @PathVariable Integer ownerId){
       requestsService.rejectRequest(requestId, ownerId);
       return ResponseEntity.status(200).body(new ApiRes("request rejected"));

    }

    @DeleteMapping("/delete/{requestId}/{playerId}")
    public ResponseEntity<?> deleteRequest(@PathVariable Integer requestId, @PathVariable Integer playerId){
       requestsService.deleteRequest(requestId, playerId);
       return ResponseEntity.status(200).body(new ApiRes("request withdrawn"));
    }


    @GetMapping("/pending/{squadId}/{ownerId}")
    public ResponseEntity<?> getPendingRequests(@PathVariable Integer squadId, @PathVariable Integer ownerId){
        List<Requests> requests = requestsService.getPendingRequests(squadId, ownerId);
        return ResponseEntity.status(200).body(requests);
    }





























}
