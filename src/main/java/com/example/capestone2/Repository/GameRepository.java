package com.example.capestone2.Repository;

import com.example.capestone2.Model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game,Integer> {

    Game findGameById(Integer id);


















}
