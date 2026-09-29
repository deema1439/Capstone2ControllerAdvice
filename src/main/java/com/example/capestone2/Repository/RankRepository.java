package com.example.capestone2.Repository;

import com.example.capestone2.Model.Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankRepository extends JpaRepository<Rank,Integer>{
    Rank findRankById(Integer id);







}
