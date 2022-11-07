package com.obamaapi.dto.responses;

import com.obamaapi.dto.requests.MenuInstance;
import com.obamaapi.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PlacedOrderResponse {
    private long orderId;
    private OrderStatus status;
    private List<MenuInstance> menuInstances;

}
