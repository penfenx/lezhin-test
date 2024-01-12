package com.lezhin.penfen.request;

import com.lezhin.penfen.type.Rating;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewRequest {
    @NotNull
    private int userId;

    @NotNull
    private int contentsId;

    @NotNull
    private Rating rating;

    private String comment;
}