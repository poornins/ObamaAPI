package com.obamaapi.repository;

import com.obamaapi.enums.Roles;
import com.obamaapi.enums.StaffAvailability;
import com.obamaapi.model.StaffDetails;
import com.obamaapi.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StaffRepository extends JpaRepository<StaffDetails,Long> {

    StaffDetails findByUserDetails_EmailAndPassword(String email, String password);
    StaffDetails findByUserDetails_UserId(long userId);
    StaffDetails findByStaffId(long staffId);
    List<StaffDetails> findAllByAvailabilityAndUserDetails_Role(StaffAvailability staffAvailability, Roles roles);

}
