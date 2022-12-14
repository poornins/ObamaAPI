package com.obamaapi.service;

import com.obamaapi.dto.requests.AddInventoryItemRequest;
import com.obamaapi.dto.requests.UpdateQuantityRequest;
import com.obamaapi.dto.requests.UpdateReorderLevelRequest;
import com.obamaapi.dto.responses.ReOrderResponse;
import com.obamaapi.model.*;
import com.obamaapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        inventoryItem.setUnit(addInventoryItemRequest.getInventoryUnits());
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
            AddInventoryItems addInventory = new AddInventoryItems();
            addInventory.setInventoryItems(item);
            addInventory.setUnitPrice(updateQuantityRequest.getUnitPrice());
            addInventory.setAddedQuantity(updateQuantityRequest.getQuantity());
            addInventory.setStaffDetails(staffRepository.findByUserDetails_UserId(updateQuantityRequest.getUserId()));
            addInventory.setDate(new Date());
            addInventoryRepository.save(addInventory);
            return item;
        }else{
            if(item.getQuantity() - updateQuantityRequest.getQuantity()<0) throw new RuntimeException("Invalid Amount!");
            item.setQuantity( item.getQuantity() - updateQuantityRequest.getQuantity());
            inventoryRepository.save(item);

            RetrieveInvetory retrieveInventory = new RetrieveInvetory();
            retrieveInventory.setInventoryItems(item);
            retrieveInventory.setRetrievedQuantity(updateQuantityRequest.getQuantity());
            retrieveInventory.setStaffDetails(staffRepository.findByUserDetails_UserId(updateQuantityRequest.getUserId()));
            retrieveInventory.setDate(new Date());
            retriveInventoryRepository.save(retrieveInventory);
            return item;
        }
    }

    public InventoryItems getInventoryItem(Long itemId) {
        return inventoryRepository.findByItemId(itemId);
    }

    public  List<InventoryItems> searchInventoryItem(Optional<String> query) {
        if(query.isPresent())
        return inventoryRepository.findAllByItemNameIsContaining(query.get());
        else
        return inventoryRepository.findAll();
    }

    public List<ReOrderResponse> getReorderItems() {
        List<InventoryItems> inventoryItems = inventoryRepository.findAll();
        List<ReOrderResponse> reOrderResponses = new ArrayList<>();
        for (InventoryItems inventoryItem : inventoryItems){
            ReOrderResponse reOrderResponse = new ReOrderResponse();
            try {
                InventoryItems items = inventoryRepository.findByItemIdAndQuantityIsLessThan(inventoryItem.getItemId(),inventoryItem.getReorderLevel());
                reOrderResponse.setItemName(items.getItemName());
                reOrderResponse.setItemId(items.getItemId());
                reOrderResponse.setLevelAdd(items.getReorderLevel() - items.getQuantity());
                reOrderResponse.setAvailQty(items.getQuantity());
                reOrderResponse.setUnits(items.getUnit());
                reOrderResponses.add(reOrderResponse);
            }catch (Exception e){
                continue;
            }
        }

        return reOrderResponses;
    }
}
