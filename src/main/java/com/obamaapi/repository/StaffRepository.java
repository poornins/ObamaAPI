package com.obamaapi.repository;

import com.obamaapi.model.StaffDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<StaffDetails,Long> {

    StaffDetails findByUserDetails_EmailAndPassword(String email, String password);
    StaffDetails findByUserDetails_UserId(Long userId);

}
