package com.lezhin.penfen.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ContentsLikes extends Contents{
    private int likes;
}
