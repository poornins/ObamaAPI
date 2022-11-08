package com.obamaapi.dto.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignedOrderResponse {
    private long orderId;
    private String contactNo;
    private int amount;
}
