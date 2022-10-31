package com.obamaapi.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuInstance {
    private long menuId;
    private String menuName;
    private int quantity;
}
