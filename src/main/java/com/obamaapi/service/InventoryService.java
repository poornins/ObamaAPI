package com.obamaapi.service;

import com.obamaapi.dto.requests.AddInventoryItemRequest;
import com.obamaapi.dto.requests.AddOrderRequest;
import com.obamaapi.dto.requests.UpdateQuantityRequest;
import com.obamaapi.dto.requests.UpdateReorderLevelRequest;
import com.obamaapi.enums.MenuAvailability;
import com.obamaapi.enums.OrderStatus;
import com.obamaapi.model.*;
import com.obamaapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private AddInventoryRepository addInventoryRepository;

    @Autowired
    private RetriveInventoryRepository retriveInventoryRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private InventoryRepository inventoryRepository;


    public boolean checkIfItemNameExists(String itemName) {
        try {
            InventoryItems i = inventoryRepository.findByItemName(itemName);
            return i != null;
        } catch (Exception e) {
            return false;
        }
    }

    public InventoryItems addInventoryItem(AddInventoryItemRequest addInventoryItemRequest) {
        if (checkIfItemNameExists(addInventoryItemRequest.getItemName()))
            throw new RuntimeException("Item Name Already Exist!");

        InventoryItems inventoryItem = new InventoryItems();
        inventoryItem.setItemName(addInventoryItemRequest.getItemName());
        inventoryItem.setReorderLevel(addInventoryItemRequest.getReOrderLevel());
        inventoryItem.setQuantity(0.00F);
        return inventoryRepository.save(inventoryItem);
    }

    public InventoryItems updateReorderLevel(String itemId, UpdateReorderLevelRequest updateReorderLevelRequest) {
       InventoryItems item = inventoryRepository.findByItemId(Long.valueOf(itemId));
       if(item==null) throw new RuntimeException("Item Not Found!");
       item.setReorderLevel(updateReorderLevelRequest.getReOrderLevel());
      return inventoryRepository.save(item);
    }

    public Object updateQuantity(String itemId, UpdateQuantityRequest updateQuantityRequest, boolean add) {
        InventoryItems item = inventoryRepository.findByItemId(Long.valueOf(itemId));
        if(item==null) throw new RuntimeException("Item Not Found!");

        if(add){
            item.setQuantity(item.getQuantity() + updateQuantityRequest.getQuantity());
            inventoryRepository.save(item);
            StaffAddInventory addInventory = new StaffAddInventory();
            addInventory.setInventoryItems(item);
            addInventory.setAddedQuantity(updateQuantityRequest.getQuantity());
            addInventory.setStaffDetails(staffRepository.findByUserDetails_UserId(updateQuantityRequest.getUserId()));
            addInventory.setDate(new Date());
            addInventoryRepository.save(addInventory);
            return item;
        }else{
            if(item.getQuantity() - updateQuantityRequest.getQuantity()<0) throw new RuntimeException("Invalid Amount!");
            item.setQuantity( item.getQuantity() - updateQuantityRequest.getQuantity());
            inventoryRepository.save(item);

            StaffRetrieveInvetory retrieveInventory = new StaffRetrieveInvetory();
            retrieveInventory.setInventoryItems(item);
            retrieveInventory.setRetrievedQuantity(updateQuantityRequest.getQuantity());
            retrieveInventory.setStaffDetails(staffRepository.findByUserDetails_UserId(updateQuantityRequest.getUserId()));
            retrieveInventory.setDate(new Date());
            retriveInventoryRepository.save(retrieveInventory);
            return item;
        }
    }
}
