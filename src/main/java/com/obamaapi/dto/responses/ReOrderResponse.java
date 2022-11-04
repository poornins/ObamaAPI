package com.obamaapi.dto.responses;

import com.obamaapi.enums.InventoryUnits;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReOrderResponse {
    private long itemId;
    private String itemName;
    private float levelAdd;
    private float availQty;
    private InventoryUnits units;

}
