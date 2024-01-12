package com.lezhin.penfen.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Contents {

    private int id;

    private String contentsName;

    private String author;

    private int coin;

    private LocalDate openDate;
}
