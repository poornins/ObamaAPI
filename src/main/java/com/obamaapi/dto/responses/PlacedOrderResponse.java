package com.obamaapi.dto.responses;

import com.obamaapi.dto.requests.MenuInstance;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PlacedOrderResponse {
    private long orderId;
    private List<MenuInstance> menuInstances;

}
