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
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotEmpty(message = "the player name Should not be Empty")
    @Size(min = 2, max = 50, message = "the player name length should be between 2 and 50")
    @Column(columnDefinition = "varchar(50) not null")
    private String name;


    @Column(columnDefinition = "double default 3.0")
    private Double rating = 3.0; //خليتها 3.0 لأنها القيمة الافتراضية المنطقية للاعب جديد

    // رانك اللاعب اختياري، يتحدد بعدين. تشيك وجوده يصير بالـ Service عن طريق RankRepository
    //لان يمكن في لا عب يدخل وهو unranked
    @Column(columnDefinition = "int")
    private Integer rankId;


    @NotEmpty(message = "email should not be empty")
    @Email(message = "email should be valid")
    @Column(columnDefinition = "varchar(100) not null unique")
    private String email;


    @NotEmpty(message = "phone should not be empty")
    @Pattern(regexp = "^9665\\d{8}$", message = "phone must be like 9665XXXXXXXX")
    @Column(columnDefinition = "varchar(12) not null ")
    private String phone;













}
