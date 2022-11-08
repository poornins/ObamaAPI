package com.obamaapi.controller;

import com.obamaapi.dto.requests.AddOrderMenuRequest;
import com.obamaapi.dto.responses.AssignedOrderResponse;
import com.obamaapi.dto.responses.PlacedOrderResponse;
import com.obamaapi.repository.OrderRepository;
import com.obamaapi.service.CustomerService;
import com.obamaapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cashier")
public class CashierController {
    @Autowired
    OrderService orderService;

    @Autowired
    CustomerService customerService;

    @GetMapping("/order/details")
    public ResponseEntity getOngoingOrders(){
        return ResponseEntity.ok().body(orderService.getOngoingOrders());
    }

    @GetMapping("/order/assigned")
    public ResponseEntity<?> getAssignedOrders(){
        List<AssignedOrderResponse> assignedOrders = orderService.getAssigned();
        if (assignedOrders.isEmpty()){
            return ResponseEntity.badRequest().body("No data");
        }else return ResponseEntity.ok().body(assignedOrders);
    }

    @PutMapping("/order/paid/{orderId}")
    public ResponseEntity<?> acceptPayment(@PathVariable long orderId){

        orderService.acceptPayment(orderId);
        return ResponseEntity.ok().body("Order Completed");
    }
}
