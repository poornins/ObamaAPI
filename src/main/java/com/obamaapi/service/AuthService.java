package com.obamaapi.service;

import com.obamaapi.dto.requests.StaffLogInRequest;
import com.obamaapi.dto.requests.StaffRegisterRequest;
import com.obamaapi.dto.responses.StaffLoginResponse;
import com.obamaapi.enums.StaffAvailability;
import com.obamaapi.model.StaffDetails;
import com.obamaapi.model.UserDetails;
import com.obamaapi.repository.StaffRepository;
import com.obamaapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StaffRepository staffRepository;

    //return true if email exists
    public boolean checkifEmailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    //add staff
    public boolean addStaff(StaffRegisterRequest staffRegisterRequest) {
        try {
            UserDetails user = new UserDetails();
            StaffDetails staff = new StaffDetails();

            user.setContactNumber(staffRegisterRequest.getContactNo());
            user.setEmail(staffRegisterRequest.getEmail());
            user.setFirstName(staffRegisterRequest.getFirstName());
            user.setLastName(staffRegisterRequest.getLastName());
            user.setRole(staffRegisterRequest.getRole());

            staff.setAvailability(StaffAvailability.NOT_AVAILABLE);
            staff.setPassword(staffRegisterRequest.getPassword());
            staff.setUserDetails(user);

            //save details in database
            userRepository.save(user);
            staffRepository.save(staff);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public StaffLoginResponse staffLogin(StaffLogInRequest staffLogInRequest) {
        UserDetails userDetails = userRepository.findByEmail(staffLogInRequest.getEmail());
        try {
            staffRepository.findByUserDetails_EmailAndPassword(staffLogInRequest.getEmail(),staffLogInRequest.getPassword());
            StaffLoginResponse staffLoginResponse = new StaffLoginResponse();
            staffLoginResponse.setUserId(userDetails.getUserId());
            staffLoginResponse.setRole(userDetails.getRole());

            return staffLoginResponse;
        }catch (Exception e){
            throw new RuntimeException("Login Credentials are invalid");
        }
    }

}
