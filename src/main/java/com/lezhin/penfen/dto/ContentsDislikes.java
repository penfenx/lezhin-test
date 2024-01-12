package com.lezhin.penfen.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ContentsDislikes extends Contents{
    private int dislikes;
}
