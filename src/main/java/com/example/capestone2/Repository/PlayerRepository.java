package com.example.capestone2.Repository;

import com.example.capestone2.Model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Integer> {

    Player findPlayerById(Integer id);
    boolean existsByRankId(Integer rankId);
    @Query("select p.name from Player p, SquadMembers m where p.id = m.playerId and m.squadId = ?1")
    List<String> findMemberNamesBySquadId(Integer squadId);
    List<Player> findTop5ByOrderByRatingDesc();











}
