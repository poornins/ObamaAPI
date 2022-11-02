package com.obamaapi.dto.responses;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AvailableStewardsResponse {
    private long userId;
    private String firstName;
}
