package com.example.capestone2.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Requests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "squad id should not be Empty")
    @Column( columnDefinition = "int not null")
    private Integer squadId;// يبغا يدخل ذا القروب

    @NotNull(message = "player id should not be Empty")
    @Column( columnDefinition = "int not null")
    private Integer playerId; // الي ارسل الدعوه

    @NotNull(message = "role id should not be Empty")
    @Column(columnDefinition = "int not null")
    private Integer roleId; // وش نوع الرول حقه

    @Column(columnDefinition = "varchar(20) not null")
    private String status = "PENDING";

    @Column(columnDefinition = "datetime not null")
    private LocalDateTime createdAt = LocalDateTime.now();


    @Column(columnDefinition = "datetime")
    private LocalDateTime respondedAt;





















}
