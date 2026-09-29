package com.example.capestone2.Repository;

import com.example.capestone2.Model.Requests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestsRepository extends JpaRepository<Requests,Integer>{

    Requests findRequestsById(Integer id);
    boolean existsByRoleId(Integer rolId);
    boolean existsBySquadIdAndPlayerIdAndStatus(Integer playerId,Integer squadId,String status);
    List<Requests>findAllBySquadIdAndStatus(Integer squadId, String status);











}
