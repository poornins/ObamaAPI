package com.obamaapi.dto.requests;

import com.obamaapi.enums.Roles;
import com.obamaapi.enums.StaffAvailability;
import lombok.Getter;

@Getter
public class StaffRegisterRequest {
    private String contactNo;
    private String email;
    private String firstName;
    private String lastName;
    private Roles role;
    private String password;

}
