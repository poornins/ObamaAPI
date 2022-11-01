package com.obamaapi.dto.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DailyStoresResponse {
    private String date;
    private String time;
    private List<StoresInstance> storesInstances;
}
