package com.lezhin.penfen.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewLogRequest {
    private int userId;
    private int contentsId;
}
