package com.obamaapi.dto.requests;

import lombok.Getter;

@Getter
public class UpdateQuantityRequest {
    private float quantity;
    private float unitPrice;
    private Long userId;
}
