package com.obamaapi.dto.requests;

import lombok.Getter;

@Getter
public class AddOrderMenuRequest {
    private long userId;
    private long orderId;
    private RequestMenuInstance[] requestMenuInstances;
}
