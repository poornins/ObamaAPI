package com.obamaapi.dto.requests;

import lombok.Getter;

@Getter
public class StaffLogInRequest {
    private String email;
    private String password;
}
