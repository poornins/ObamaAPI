package com.obamaapi.dto.requests;

import com.obamaapi.enums.InventoryUnits;
import com.obamaapi.enums.MenuType;
import lombok.Getter;

@Getter
public class AddInventoryItemRequest {
    private String itemName;
    private float reOrderLevel;
    private InventoryUnits inventoryUnits;
}
