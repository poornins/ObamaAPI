package com.obamaapi.controller;

import com.obamaapi.dto.requests.AddMenuRequest;
import com.obamaapi.model.MenuItems;
import com.obamaapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    OrderService orderService;

    @PostMapping("/menu/add")
    public ResponseEntity addMenu(@RequestBody AddMenuRequest addMenuRequest){

        if(orderService.checkIfMenuNameExists(addMenuRequest.getMenuName())){
            return ResponseEntity.badRequest().body("Menu Exists");
        }else {
            orderService.addMenu(addMenuRequest);
            return ResponseEntity.ok().body("Menu Added");
        }
    }

    @GetMapping("/menulist/view")
    public ResponseEntity getMenuList(){
            List<MenuItems> menuItems = orderService.getMenuList();
            if (menuItems != null){
                return ResponseEntity.ok().body(menuItems);
            }else
            return ResponseEntity.badRequest().body("No items added");
    }
    @PutMapping("/menu/availability/{menuId}")
    public ResponseEntity updateAvailability(@PathVariable long menuId){
        try {
            orderService.setAvailability(menuId);
            return ResponseEntity.ok().body("Availability Updated");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error in Update");
        }
    }

}
