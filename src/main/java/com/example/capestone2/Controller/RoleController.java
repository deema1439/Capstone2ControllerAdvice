package com.example.capestone2.Controller;

import com.example.capestone2.Api.ApiRes;
import com.example.capestone2.Model.Role;
import com.example.capestone2.Service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    @GetMapping("/get")
    public ResponseEntity<?> getRoles(){
        return ResponseEntity.status(200).body(roleService.getRoles());
    }


    @PostMapping("/add")
    public ResponseEntity<?> addRole(@RequestBody @Valid Role role){
        roleService.addRole(role);
        return ResponseEntity.status(200).body(new ApiRes("role added"));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Integer id, @RequestBody @Valid Role role){
               roleService.updateRole(id, role);
                return ResponseEntity.status(200).body(new ApiRes("role updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Integer id){
        roleService.deleteRole(id);
        return ResponseEntity.status(200).body(new ApiRes("role deleted"));
    }










}
