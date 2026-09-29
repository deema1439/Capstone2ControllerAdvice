package com.example.capestone2.Repository;

import com.example.capestone2.Model.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews,Integer>{

    Reviews findReviewsById(Integer id);

    boolean existsByReviewerIdAndRevieweeIdAndSquadId(Integer reviewerId, Integer revieweeId, Integer squadId);
    @Query("select avg(r.rating) from Reviews r where r.revieweeId = ?1")
    Double getAverageRating(Integer revieweeId);

    List<Reviews>findAllByRevieweeId(Integer  revieweeId);







}
