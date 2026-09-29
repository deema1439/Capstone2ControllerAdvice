package com.example.capestone2.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Squad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Squad Name Should Not Be Empty")
    @Column(columnDefinition = "varchar(100) not null ")
    private String name;

    @NotNull(message = "game id should not be Empty")
    @Column( columnDefinition = "int not null")
    private Integer gameId;

    // رئيس القروب هو اللي يقبل ويرفض اللاعبين  تشيك وجوده يصير بالـ Service عن طريق PlayerRepository
    @NotNull(message = "owner id should not be Empty")
    @Column( columnDefinition = "int not null")
    private Integer ownerId;

    @Column(columnDefinition = "double")
    private Double compatibilityScore;

    @Column(columnDefinition = "varchar(20) not null")
    private String status = "OPEN";

    @Column(columnDefinition = "datetime not null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();













}
