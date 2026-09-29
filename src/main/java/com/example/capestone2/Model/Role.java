package com.example.capestone2.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Game id should not be Empty")
    @Column(columnDefinition = "int not null ")
    private Integer gameId;//Fk for game need GameRepo in role


    @NotEmpty(message = "the role name Should not be Empty")
    @Size(min = 2, max = 30, message = "the role name length should be between 2 and 30")
    @Column(columnDefinition = "varchar(30) not null")
    private String name;


}
