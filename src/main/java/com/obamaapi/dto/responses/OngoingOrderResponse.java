package com.obamaapi.dto.responses;

import com.obamaapi.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OngoingOrderResponse {
    private long orderId;
    private String name;
    private int amount;
    private String contactNo;
    private OrderStatus orderStatus;
}
