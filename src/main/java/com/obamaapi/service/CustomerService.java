package com.obamaapi.service;

import com.obamaapi.dto.responses.CustomerCountsResponse;
import com.obamaapi.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public ResponseEntity getCustomerCount(){
        CustomerCountsResponse customerCountsResponse=new CustomerCountsResponse();
        int totalCount = customerRepository.totalCustomerCount();
        int todayCustomerCount = customerRepository.totalCustomerCount();
        customerCountsResponse.setTotalCustomerCount(totalCount);
        return null;
    }
}
