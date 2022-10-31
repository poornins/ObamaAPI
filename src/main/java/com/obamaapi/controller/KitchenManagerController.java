package com.obamaapi.controller;

import com.obamaapi.dto.responses.PlacedOrderResponse;
import com.obamaapi.model.MenuItems;
import com.obamaapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/km")
public class KitchenManagerController {

    @Autowired
    OrderService orderService;

    @GetMapping("/order/requests")
    public ResponseEntity<?> getPlacedOrders(){
        List<PlacedOrderResponse> placedOrderResponses = orderService.getPlacedOrders();
        for (PlacedOrderResponse placedOrderResponse:placedOrderResponses
             ) {
            System.out.println(placedOrderResponse.getOrderId());
        }
        if (placedOrderResponses == null){
            return ResponseEntity.badRequest().body("No data");
        }else return ResponseEntity.ok().body(placedOrderResponses);
    }
}
