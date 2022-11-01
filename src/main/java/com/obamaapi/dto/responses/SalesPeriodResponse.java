package com.obamaapi.dto.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SalesPeriodResponse {
    private String toDate;
    private String fromDate;
    private int total;
    private List<SalesInstance> salesInstances;
}
