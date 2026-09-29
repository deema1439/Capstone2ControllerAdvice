package com.example.capestone2.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "the game name Should not be Empty")
    @Size(min = 2,max = 70 ,message = "the game name length max should be between 2 and 70")
    @Column(columnDefinition = "varchar(70) not null ",unique = true)//تكون اسم اللعبه يونيك بالصف
    private String name;

    @NotEmpty(message = "the game genre Should not be Empty")
    @Size(min = 2,max = 40,message = "the genre name length should be between 2 and 40 ")
    @Column(columnDefinition = "varchar(40) not null")
    private String genre;


    @NotNull(message = "max Player should not be Empty")
    @Min(value = 2,message = "max player should be at least 2 or more ")
    @Column(columnDefinition = "int not null ")
    private Integer maxPlayer;











}
