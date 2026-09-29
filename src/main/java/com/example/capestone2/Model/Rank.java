package com.example.capestone2.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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
public class Rank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;



    @NotNull(message = "Game id should not be Empty")
    @Column(columnDefinition = "int not null ")
    private Integer gameId;//Fk for game need GameRepo in rank


    @NotEmpty(message = "the rank name Should not be Empty")
    @Size(min = 2, max = 30, message = "the rank name length should be between 2 and 30")
    @Column(columnDefinition = "varchar(30) not null")
    private String name;


    @NotNull(message = "tier order should not be Empty")   // لازم تنحط قيمة
    @Min(value = 1, message = "tier order should be at least 1")  // أقل رقم مسموح 1
    @Column(columnDefinition = "int not null")
    private Integer tierOrder;//ترتيب الرانكات bronze اقل من gold وكذا








}
