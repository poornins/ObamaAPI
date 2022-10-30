package com.obamaapi.dto.responses;

import com.obamaapi.enums.Roles;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StaffLoginResponse {
    private long userId;
    private Roles role;

}
