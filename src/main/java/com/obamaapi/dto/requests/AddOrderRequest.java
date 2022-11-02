package com.obamaapi.dto.requests;

import com.obamaapi.model.CustomerDetails;
import lombok.Getter;

@Getter
public class AddOrderRequest {
    private int placementId;
    private int amount;
    private long userId;

}
