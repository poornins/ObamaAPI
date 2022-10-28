package com.obamaapi.dto.requests;

import com.obamaapi.enums.MenuType;
import lombok.Getter;

@Getter
public class AddMenuRequest {
    private String menuName;
    private String price;
    private MenuType menuType;
    private String imageUrl;
}
