package com.obamaapi.controller;

import com.obamaapi.dto.requests.AddInventoryItemRequest;
import com.obamaapi.dto.requests.AddMenuRequest;
import com.obamaapi.dto.requests.UpdateQuantityRequest;
import com.obamaapi.dto.requests.UpdateReorderLevelRequest;
import com.obamaapi.model.InventoryItems;
import com.obamaapi.service.InventoryService;
import com.obamaapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    InventoryService inventoryService;

    @PostMapping("/item")
    public ResponseEntity<?> addInventoryItem(@RequestBody AddInventoryItemRequest addInventoryItemRequest){
        try{
            return  ResponseEntity.ok().body(inventoryService.addInventoryItem(addInventoryItemRequest));
        }catch (RuntimeException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/item/rlevel/{itemId}")
    public ResponseEntity<?> updateReorderLevel(@RequestBody UpdateReorderLevelRequest updateReorderLevelRequest, @PathVariable String itemId){
        try{
            return  ResponseEntity.ok().body(inventoryService.updateReorderLevel(itemId,updateReorderLevelRequest));
        }catch (RuntimeException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/quantity/add/{itemId}")
    public ResponseEntity<?> addQuantity(@RequestBody UpdateQuantityRequest updateQuantityRequest, @PathVariable String itemId){
        try{
            return  ResponseEntity.ok().body(inventoryService.updateQuantity(itemId,updateQuantityRequest,true));
        }catch (RuntimeException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/quantity/consume/{itemId}")
    public ResponseEntity<?> ConsumeQuantity(@RequestBody UpdateQuantityRequest updateQuantityRequest, @PathVariable String itemId){
        try{
            return  ResponseEntity.ok().body(inventoryService.updateQuantity(itemId,updateQuantityRequest,false));
        }catch (RuntimeException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
