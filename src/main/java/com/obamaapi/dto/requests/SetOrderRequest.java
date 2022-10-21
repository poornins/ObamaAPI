package com.obamaapi.dto.requests;

import lombok.Getter;

@Getter
public class SetOrderRequest {
    private int placementId;
    private int userId;
    private int amount;


}
