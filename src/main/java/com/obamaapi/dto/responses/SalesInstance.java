package com.obamaapi.dto.responses;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SalesInstance {
    private long itemNo;
    private String menuName;
    private int quantity;
    private String unitPrice;
    private int total;
}
