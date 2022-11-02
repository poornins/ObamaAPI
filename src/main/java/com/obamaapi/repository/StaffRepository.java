package com.obamaapi.repository;

import com.obamaapi.enums.Roles;
import com.obamaapi.enums.StaffAvailability;
import com.obamaapi.model.StaffDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StaffRepository extends JpaRepository<StaffDetails,Long> {

    StaffDetails findByUserDetails_EmailAndPassword(String email, String password);
    StaffDetails findByUserDetails_UserId(Long userId);
    List<StaffDetails> findAllByAvailabilityAndUserDetails_Role(StaffAvailability staffAvailability, Roles roles);

}
