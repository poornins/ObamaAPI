package com.obamaapi.controller;

import com.obamaapi.dto.requests.AddMenuRequest;
import com.obamaapi.dto.requests.AddOrderMenuRequest;
import com.obamaapi.dto.requests.AddOrderRequest;
import com.obamaapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/place/order")
    public ResponseEntity placeOrder(@RequestBody AddOrderRequest addOrderRequest){

            return ResponseEntity.ok().body(orderService.placeOrder(addOrderRequest));
    }

    @PostMapping("/add/menu instance")
    public ResponseEntity addOrderMenu(@RequestBody AddOrderMenuRequest addOrderMenuRequest){
        orderService.addOrderMenu(addOrderMenuRequest);
        return ResponseEntity.ok().body("Menu-Order Added");
    }
}
