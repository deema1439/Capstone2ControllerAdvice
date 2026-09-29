package com.example.capestone2.Controller;

import com.example.capestone2.Api.ApiRes;
import com.example.capestone2.Model.Reviews;
import com.example.capestone2.Service.ReviewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewsController {
    private final ReviewsService reviewsService;
    @GetMapping("/get")
    public ResponseEntity<?> getReviews(){
        return ResponseEntity.status(200).body(reviewsService.getReviews());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addReviews(@RequestBody @Valid Reviews reviews){

         reviewsService.addReviews(reviews);
         return ResponseEntity.status(200).body(new ApiRes("review added"));

    }

    @PutMapping("/update/{id}/{reviewerId}")
    public ResponseEntity<?> updateReviews(@PathVariable Integer id, @PathVariable Integer reviewerId,
                                           @RequestBody @Valid Reviews reviews){
        reviewsService.updateReviews(id, reviewerId, reviews);
      return ResponseEntity.status(200).body(new ApiRes("review updated"));

    }



    @DeleteMapping("/delete/{id}/{reviewerId}")
    public ResponseEntity<?> deleteReviews(@PathVariable Integer id, @PathVariable Integer reviewerId){
        reviewsService.deleteReviews(id, reviewerId);
        return ResponseEntity.status(200).body(new ApiRes("review deleted"));

    }

    @GetMapping("/summary/{playerId}")
    public ResponseEntity<?> summarizePlayerReviews(@PathVariable Integer playerId){
        String summary = reviewsService.summarizePlayerReviews(playerId);
        return ResponseEntity.status(200).body(new ApiRes(summary));
    }

}
