package com.obamaapi.controller;

import com.obamaapi.dto.requests.AddMenuRequest;
import com.obamaapi.model.MenuItems;
import com.obamaapi.service.OrderService;
import com.obamaapi.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    OrderService orderService;

    @Autowired
    SalesService salesService;

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

//    -----------------REPORTS----------------------------------


    @GetMapping("/SalesReport/Daily/{date}")
    public ResponseEntity getSalesForDate(@PathVariable String date){

       return ResponseEntity.ok().body(salesService.getDailySales(date));

    }

    @GetMapping("/salesReport/period/{fromDate}/{toDate}")
    public ResponseEntity getSalesForPeriod(@PathVariable String fromDate,@PathVariable String toDate){

        return ResponseEntity.ok().body(salesService.getSalesPeriod(fromDate,toDate));

    }

//    @GetMapping("/salesReport/period/{forMonth}")
//    public ResponseEntity getSalesForMonth(@PathVariable String fromDate,@PathVariable String toDate){
//
//        return ResponseEntity.ok().body(salesService.getSalesPeriod(fromDate,toDate));
//
//    }

//    ----------------------Stores Reports----------------------------------

    @GetMapping("/storeReport/daily")
    public ResponseEntity getStoresReport(){

        return ResponseEntity.ok().body(salesService.getDailyStores());

    }
}
