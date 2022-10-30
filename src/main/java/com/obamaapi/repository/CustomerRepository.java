package com.obamaapi.repository;

import com.obamaapi.model.CustomerDetails;
import com.obamaapi.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerDetails, Long> {
    CustomerDetails findByUserDetails_UserId(long userId);
    CustomerDetails findByUserDetailsUserId(long userId);
    CustomerDetails findByUserDetailsContactNumber(String contactNo);
}
