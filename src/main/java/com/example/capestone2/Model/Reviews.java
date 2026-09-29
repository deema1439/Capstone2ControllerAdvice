package com.example.capestone2.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "squad id should not be Empty")
    @Column( columnDefinition = "int not null")
    private Integer squadId;


    @NotNull(message = "reviewer id should not be Empty")
    @Column(columnDefinition = "int not null")
    private Integer reviewerId;

    @NotNull(message = "reviewee id should not be Empty")
    @Column( columnDefinition = "int not null")
    private Integer revieweeId;

    @NotNull(message = "rating should not be Empty")
    @Min(value = 1, message = "rating should be at least 1")
    @Max(value = 5, message = "rating should be at most 5")
    @Column(columnDefinition = "int not null")
    private Integer rating;

    @Size(max = 255, message = "comment length should be at most 255")
    @Column(columnDefinition = "varchar(255)")
    private String comment;

    @Column(columnDefinition = "datetime not null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();


}
