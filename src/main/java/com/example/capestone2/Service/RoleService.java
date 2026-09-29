package com.example.capestone2.Service;

import com.example.capestone2.Api.ApiException;
import com.example.capestone2.Model.Game;
import com.example.capestone2.Model.Role;
import com.example.capestone2.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final GameRepository gameRepository;
    private final RequestsRepository requestsRepository;
    private final OffersRepository offersRepository;
    private final SquadMembersRepository squadMembersRepository;

    public List<Role>getRoles(){
        return roleRepository.findAll();
    }


    public void addRole(Role role){
        Game existingGame=gameRepository.findGameById(role.getGameId());
            if(existingGame==null){
              throw new ApiException("game not found");
            }
            roleRepository.save(role);
    }


    public void updateRole(Integer id,Role role) {
        Role oldRole = roleRepository.findRoleById(id);
        Game existingGame = gameRepository.findGameById(role.getGameId());
        if (oldRole == null) {
            throw new ApiException("role not found");
        }
        if (existingGame == null) {
            throw new ApiException("game Id not found");
        }
        if (!oldRole.getGameId().equals(role.getGameId())) {
            if (isRoleInUse(id)) {
               throw new ApiException(" role is in use, cannot change its game");
            }
        }
            oldRole.setName(role.getName());
            oldRole.setGameId(role.getGameId());
            roleRepository.save(oldRole);
    }

    public void deleteRole(Integer id) {
        Role existingRole = roleRepository.findRoleById(id);
        if (existingRole == null) {
           throw new ApiException("role not found");
        }
        if (isRoleInUse(id)) {
           throw new ApiException("role is in use, cannot delete");
        }
        roleRepository.delete(existingRole);
    }

       public boolean isRoleInUse(Integer roleId) {
            return requestsRepository.existsByRoleId(roleId)
                    || offersRepository.existsByRoleId(roleId)
                    || squadMembersRepository.existsByRoleId(roleId);
        }







    }

















