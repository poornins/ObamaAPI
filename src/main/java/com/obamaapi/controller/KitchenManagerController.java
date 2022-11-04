package com.obamaapi.controller;

import com.obamaapi.dto.responses.PlacedOrderResponse;
import com.obamaapi.model.MenuItems;
import com.obamaapi.service.OrderService;
import com.obamaapi.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/km")
public class KitchenManagerController {

    @Autowired
    OrderService orderService;

    @Autowired
    StaffService staffService;

    @GetMapping("/order/requests")
    public ResponseEntity<?> getPlacedOrders(){
        List<PlacedOrderResponse> placedOrderResponses = orderService.getPlacedOrders();
        for (PlacedOrderResponse placedOrderResponse:placedOrderResponses
             ) {
            System.out.println(placedOrderResponse.getOrderId());
        }
        if (placedOrderResponses.isEmpty()){
            return ResponseEntity.badRequest().body("No data");
        }else return ResponseEntity.ok().body(placedOrderResponses);
    }

    @PutMapping("/order/accept/{orderId}")
    public ResponseEntity<?> acceptOrder(@PathVariable long orderId){

        orderService.acceptOrder(orderId);
        return ResponseEntity.ok().body("Order Accepted");
    }

    @PutMapping("/order/prepare/{orderId}")
    public ResponseEntity<?> preparedOrder(@PathVariable long orderId){

        orderService.prepareOrder(orderId);
        return ResponseEntity.ok().body("Order Prepared");
    }

    @GetMapping("/order/accepted")
    public ResponseEntity<?> getAcceptedOrders(){
        List<PlacedOrderResponse> acceptedOrderResponses = orderService.getAccepted();
        for (PlacedOrderResponse placedOrderResponse:acceptedOrderResponses
        ) {
            System.out.println(placedOrderResponse.getOrderId());
        }
        if (acceptedOrderResponses.isEmpty()){
            return ResponseEntity.badRequest().body("No data");
        }else return ResponseEntity.ok().body(acceptedOrderResponses);
    }

    @GetMapping("/order/assign/{userId}/{orderId}")
    public ResponseEntity<?> assignOrder(@PathVariable long userId, @PathVariable long orderId){
        orderService.assignOrder(userId,orderId);
        return ResponseEntity.ok().body("Order Assigned");
    }

    @GetMapping("/steward/available")
    public ResponseEntity<?> getAvaialableSteward(){

        return ResponseEntity.ok().body(staffService.getAvailableStewards());

    }

}
