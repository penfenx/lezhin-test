package com.lezhin.penfen.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "Contents")
public class Contents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "contents_name")
    private String contentsName;

    @Column(name = "author")
    private String author;

    @Column(name = "coin")
    private int coin;

    @Column(name = "open_date")
    private LocalDate openDate;

    @Column(name = "is_adult")
    private boolean isAdult;

}