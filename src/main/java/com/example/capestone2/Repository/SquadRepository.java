package com.example.capestone2.Repository;

import com.example.capestone2.Model.Squad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SquadRepository extends JpaRepository<Squad,Integer>{
    Squad findSquadById(Integer id);
    boolean existsByOwnerId(Integer ownerId);
    boolean existsByGameId(Integer gameId);
    @Query("select s from Squad s, SquadMembers m where s.id = m.squadId and m.playerId = ?1")
    List<Squad> findSquadsByPlayerId(Integer playerId);

}
