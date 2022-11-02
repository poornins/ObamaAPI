package com.obamaapi.service;

import com.obamaapi.dto.responses.CustomerCountsResponse;
import com.obamaapi.enums.OrderStatus;
import com.obamaapi.repository.CustomerRepository;
import com.obamaapi.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    public CustomerCountsResponse getCustomerCount(){
        LocalDate today = LocalDate.now();
        System.out.println(today.toString());
        CustomerCountsResponse customerCountsResponse=new CustomerCountsResponse();
        int totalCount = customerRepository.totalCustomerCount();
        int todayNewCustomerCount = customerRepository.todayNewCustomerCount(today.toString());
        int todayOrderCount = orderRepository.todayOrders(today.toString());
        int pendingOrderCount = orderRepository.pendingOrders();
        customerCountsResponse.setTotalCustomerCount(totalCount);
        customerCountsResponse.setTodayNewCustomerCount(todayNewCustomerCount);
        customerCountsResponse.setTodayOrderCount(todayOrderCount);
        customerCountsResponse.setPendingOrderCount(pendingOrderCount);

        return customerCountsResponse;
    }
}
