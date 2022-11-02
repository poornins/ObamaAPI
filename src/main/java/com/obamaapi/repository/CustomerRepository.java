package com.obamaapi.repository;

import com.obamaapi.model.CustomerDetails;
import com.obamaapi.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<CustomerDetails, Long> {
    CustomerDetails findByUserDetails_UserId(long userId);
    CustomerDetails findByUserDetailsContactNumber(String contactNo);
    @Query(value = "SELECT COUNT(customer_id) FROM `customer_details`;" , nativeQuery = true)
    int totalCustomerCount();

    @Query(value = "SELECT COUNT(customer_id) FROM `customer_details`;" , nativeQuery = true)
    int todayCustomerCount();
}
