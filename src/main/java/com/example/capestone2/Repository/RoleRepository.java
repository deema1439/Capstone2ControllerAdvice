package com.example.capestone2.Repository;

import com.example.capestone2.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer>{

Role findRoleById(Integer id);





}
