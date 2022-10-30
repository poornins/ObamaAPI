package com.obamaapi.controller;

import com.obamaapi.dto.requests.AddMenuRequest;
import com.obamaapi.dto.requests.AddOrderRequest;
import com.obamaapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/place/order")
    public ResponseEntity placeOrder(@RequestBody AddOrderRequest addOrderRequest){
            orderService.placeOrder(addOrderRequest);
            return ResponseEntity.ok().body("Order Added");
    }

}
