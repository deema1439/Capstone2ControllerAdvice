package com.example.capestone2.Service;

import com.example.capestone2.Api.ApiException;
import com.example.capestone2.Model.Player;
import com.example.capestone2.Model.Reviews;
import com.example.capestone2.Model.Squad;
import com.example.capestone2.Repository.PlayerRepository;
import com.example.capestone2.Repository.ReviewsRepository;
import com.example.capestone2.Repository.SquadMembersRepository;
import com.example.capestone2.Repository.SquadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewsService {
   private final ReviewsRepository reviewsRepository;
   private final SquadRepository squadRepository;
   private final PlayerRepository playerRepository;
   private final SquadMembersRepository squadMembersRepository;
   private final AiService aiService;

   public List<Reviews>getReviews(){
      return reviewsRepository.findAll();
   }

   public void addReviews(Reviews reviews){
      Player reviewer = playerRepository.findPlayerById(
              reviews.getReviewerId());
      if(reviewer == null){
         throw new ApiException("reviewer not found");
      }
      Player reviewee = playerRepository.findPlayerById(reviews.getRevieweeId());
      if(reviewee == null) {
         throw new ApiException("reviewed not found");
      }
         Squad squad = squadRepository.findSquadById(reviews.getSquadId());
         if (squad == null) {
            throw new ApiException("squad not found");
         }
         if (reviews.getReviewerId().equals(reviews.getRevieweeId())) {
            throw new ApiException("can't review yourself");
         } // can't review yourself

         // the owner counts as a member too
         boolean reviewerIn = squad.getOwnerId().equals(reviews.getReviewerId())
                 || squadMembersRepository.existsBySquadIdAndPlayerId(squad.getId(), reviews.getReviewerId());
         boolean revieweeIn = squad.getOwnerId().equals(reviews.getRevieweeId())
                 || squadMembersRepository.existsBySquadIdAndPlayerId(squad.getId(), reviews.getRevieweeId());
         if (!reviewerIn || !revieweeIn) {
            throw new ApiException("both players must be in the same squad");
         }

         if (reviewsRepository.existsByReviewerIdAndRevieweeIdAndSquadId(
                 reviews.getReviewerId(), reviews.getRevieweeId(), squad.getId())) {
            throw new ApiException("already reviewed this player in this squad");
         }
         reviews.setCreatedAt(LocalDateTime.now());
         reviewsRepository.save(reviews);
         updatePlayerRating(reviews.getRevieweeId());
      }


   public void updateReviews(Integer id, Integer reviewerId, Reviews reviews){
      Reviews oldReview = reviewsRepository.findReviewsById(id);
      if(oldReview == null){
         throw new ApiException("review not found"); // review not found
      }
      if(!oldReview.getReviewerId().equals(reviewerId)){
         throw new ApiException("only the reviewer can update this review"); // only the reviewer can update this review
      }

      oldReview.setRating(reviews.getRating());
      oldReview.setComment(reviews.getComment());
      reviewsRepository.save(oldReview);
      updatePlayerRating(oldReview.getRevieweeId());
   }


   public void deleteReviews(Integer id, Integer reviewerId){
      Reviews review = reviewsRepository.findReviewsById(id);
      if(review == null){
         throw new ApiException("review not found");// review not found
      }
      if(!review.getReviewerId().equals(reviewerId)){
        throw new ApiException("only the reviewer can delete this review");
      }
      reviewsRepository.delete(review);
      updatePlayerRating(review.getRevieweeId());
   }




   private void updatePlayerRating(Integer playerId){
      Player player = playerRepository.findPlayerById(playerId);
      if(player == null){
         throw new ApiException("player not found");
      }
      Double average = reviewsRepository.getAverageRating(playerId);
      if(average == null){
         player.setRating(3.0);
      } else {
         player.setRating(average);
      }
      playerRepository.save(player);
   }



   public String summarizePlayerReviews(Integer playerId){
      Player p=playerRepository.findPlayerById(playerId);
      if(p==null){
         throw new ApiException("Player Not Found");
      }
      List<Reviews> reviews=reviewsRepository.findAllByRevieweeId(playerId);
       if(reviews.isEmpty()){
          throw new ApiException("No Reviews Yet");
       }

      String comments = "";

       for(Reviews r:reviews){
          comments +="- rating "+r.getRating()+": "+r.getComment()+ "\n";
       }
      String prompt ="These are teammates' reviews of a gamer named " + p.getName()
              + ". Summarize them in 2 short sentences " + comments;
       return aiService.ask(prompt);

   }










}
