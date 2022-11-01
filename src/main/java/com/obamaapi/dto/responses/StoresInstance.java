package com.obamaapi.dto.responses;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StoresInstance {
    private long itemCode;
    private String itemName;
    private float qtyAvailable;
    private float qtyUsed;
}
