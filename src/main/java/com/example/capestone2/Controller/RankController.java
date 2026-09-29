package com.example.capestone2.Controller;

import com.example.capestone2.Api.ApiRes;
import com.example.capestone2.Model.Rank;
import com.example.capestone2.Service.RankService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rank")
@RequiredArgsConstructor
public class RankController {
    private final RankService rankService;

    @GetMapping("/get")
    public ResponseEntity<?> getRanks(){
        return ResponseEntity.status(200).body(rankService.getRanks());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRank(@RequestBody @Valid Rank rank){
        rankService.addRank(rank);
        return ResponseEntity.status(200).body(new ApiRes("rank added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRank(@PathVariable Integer id, @RequestBody @Valid Rank rank){
        rankService.updateRank(id, rank);
        return ResponseEntity.status(200).body(new ApiRes("rank updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRank(@PathVariable Integer id){
                rankService.deleteRank(id);
                return ResponseEntity.status(200).body(new ApiRes("rank deleted"));
    }






















}
