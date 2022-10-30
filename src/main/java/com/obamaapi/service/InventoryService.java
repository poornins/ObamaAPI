package com.obamaapi.service;

import com.obamaapi.dto.requests.AddInventoryItemRequest;
import com.obamaapi.dto.requests.AddOrderRequest;
import com.obamaapi.dto.requests.UpdateReorderLevelRequest;
import com.obamaapi.enums.MenuAvailability;
import com.obamaapi.enums.OrderStatus;
import com.obamaapi.model.CustomerDetails;
import com.obamaapi.model.InventoryItems;
import com.obamaapi.model.MenuItems;
import com.obamaapi.model.OrderDetails;
import com.obamaapi.repository.CustomerRepository;
import com.obamaapi.repository.InventoryRepository;
import com.obamaapi.repository.MenuRepository;
import com.obamaapi.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private OrderRepository orderRepository;

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

    public List<MenuItems> getMenuList() {
        List<MenuItems> menuItemsList = menuRepository.findAll();
        return menuItemsList;
    }

    public void setAvailability(long menuId) {
        MenuItems menu = menuRepository.findByMenuId(menuId);
        if (menu.getAvailability().equals(MenuAvailability.AVAILABLE)) {
            menu.setAvailability(MenuAvailability.NOT_AVAILABLE);
        } else {
            menu.setAvailability(MenuAvailability.AVAILABLE);
        }
        menuRepository.save(menu);
    }

    public void placeOrder(AddOrderRequest addOrderRequest) {
        OrderDetails orderDetails = new OrderDetails();
        CustomerDetails customerDetails;

        customerDetails = customerRepository.findByUserDetailsUserId(addOrderRequest.getUserId());
        orderDetails.setPlacementId(addOrderRequest.getPlacementId());
        orderDetails.setAmount(addOrderRequest.getAmount());
        orderDetails.setStatus(OrderStatus.PLACED);
        orderDetails.setCustomerDetails(customerDetails);
        orderRepository.save(orderDetails);
    }

    public InventoryItems updateReorderLevel(String itemId, UpdateReorderLevelRequest updateReorderLevelRequest) {
       InventoryItems item = inventoryRepository.findByItemId(Long.valueOf(itemId));
       if(item==null) throw new RuntimeException("Item Not Found!");
       item.setReorderLevel(updateReorderLevelRequest.getReOrderLevel());
      return inventoryRepository.save(item);
    }
}
