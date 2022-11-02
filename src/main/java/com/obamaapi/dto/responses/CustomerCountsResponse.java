package com.obamaapi.dto.responses;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerCountsResponse {
    private int totalCustomerCount;
    private int todayNewCustomerCount;
    private int todayOrderCount;
    private int pendingOrderCount;
}
