package com.example.capestone2.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class SquadMembers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "squad id should not be Empty")
    @Column(columnDefinition = "int not null")
    private Integer squadId;

    @NotNull(message = "player id should not be Empty")
    @Column(columnDefinition = "int not null")
    private Integer playerId;


    @NotNull(message = "role id should not be Empty")
    @Column( columnDefinition = "int not null")
    private Integer roleId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "datetime not null")
    private LocalDateTime joinedAt = LocalDateTime.now();



}
