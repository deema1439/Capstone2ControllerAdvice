package com.example.capestone2.Repository;

import com.example.capestone2.Model.SquadMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SquadMembersRepository extends JpaRepository<SquadMembers,Integer> {

    SquadMembers findSquadMembersById(Integer id);
    SquadMembers findSquadMembersBySquadIdAndPlayerId(Integer squadId,Integer playerId);
    boolean existsByRoleId(Integer rolId);
    void deleteBySquadId(Integer squadId);
    boolean existsBySquadIdAndPlayerId(Integer squadId, Integer playerId);
    long countBySquadId(Integer squadId);
    boolean existsByPlayerId(Integer id);
    List<SquadMembers> findAllBySquadId(Integer squadId);








}
