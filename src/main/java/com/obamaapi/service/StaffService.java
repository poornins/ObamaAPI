package com.obamaapi.service;

import com.obamaapi.dto.responses.AvailableStewardsResponse;
import com.obamaapi.enums.Roles;
import com.obamaapi.enums.StaffAvailability;
import com.obamaapi.model.StaffDetails;
import com.obamaapi.model.UserDetails;
import com.obamaapi.repository.StaffRepository;
import com.obamaapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StaffService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StaffRepository staffRepository;

    public List<StaffDetails> getAvailableStewards(){
        try {
            return staffRepository.findAllByAvailabilityAndUserDetails_Role(StaffAvailability.AVAILABLE,Roles.STEWARD);
        }catch (Exception e){
            throw new RuntimeException("Stewards Unavailable");
        }
    }
}
