package com.obamaapi.controller;

import com.obamaapi.dto.requests.StaffLogInRequest;
import com.obamaapi.dto.requests.StaffRegisterRequest;
import com.obamaapi.dto.responses.StaffLoginResponse;
import com.obamaapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    //Staff add
    @PostMapping("/staff/signup")
    public ResponseEntity signup(@RequestBody StaffRegisterRequest staffRegisterRequest) {

        if(authService.checkifEmailExists(staffRegisterRequest.getEmail())){
            return ResponseEntity.badRequest().body("Email Exists");
        }

        if(authService.addStaff(staffRegisterRequest)){
            return ResponseEntity.ok().body("User Added");
        }else {
            return ResponseEntity.badRequest().body("User Signup Failed");
        }
    }

    @PostMapping("/staff/login")
    public ResponseEntity<?> login(@RequestBody StaffLogInRequest staffLogInRequest) {

        String email = staffLogInRequest.getEmail();
        //continue if staff exists on provided details
        if (authService.checkifEmailExists(email)) {
            try {
                StaffLoginResponse response = authService.staffLogin(staffLogInRequest);
                return ResponseEntity.ok(response);
            }catch (RuntimeException e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        } else {
            return ResponseEntity.badRequest().body("UserName or email Invalid");

        }
    }

    @GetMapping("/customer/login/{contactNo}")
    public ResponseEntity<?> customerLogin(@PathVariable String contactNo) {
        if (!authService.checkifCustomerExists(contactNo)) {
            authService.addCustomer(contactNo);
        }
        return ResponseEntity.ok().body(authService.getCustomerUserId(contactNo));
    }
}
