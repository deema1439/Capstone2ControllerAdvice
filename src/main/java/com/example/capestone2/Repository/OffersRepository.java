package com.example.capestone2.Repository;

import com.example.capestone2.Model.Offers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OffersRepository extends JpaRepository<Offers,Integer> {

    Offers findOffersById(Integer id);
    boolean existsByRoleId(Integer rolId);
    boolean existsBySquadIdAndPlayerIdAndStatus(Integer squadId, Integer playerId, String status);
    List<Offers>findAllByPlayerIdAndStatus(Integer playerId, String status);


}
