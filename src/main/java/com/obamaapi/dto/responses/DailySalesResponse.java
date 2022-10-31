package com.obamaapi.dto.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class DailySalesResponse {
    private String date;
    private int total;
    private List<SalesInstance> salesInstances;
}
