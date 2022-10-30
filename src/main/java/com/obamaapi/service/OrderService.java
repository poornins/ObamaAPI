package com.obamaapi.service;

import com.obamaapi.dto.requests.AddMenuRequest;
import com.obamaapi.dto.requests.AddOrderMenuRequest;
import com.obamaapi.dto.requests.AddOrderRequest;
import com.obamaapi.dto.requests.RequestMenuInstance;
import com.obamaapi.enums.MenuAvailability;
import com.obamaapi.enums.OrderStatus;
import com.obamaapi.model.CustomerDetails;
import com.obamaapi.model.MenuItems;
import com.obamaapi.model.OrderDetails;
import com.obamaapi.model.OrderIncludesMenu;
import com.obamaapi.repository.CustomerRepository;
import com.obamaapi.repository.MenuRepository;
import com.obamaapi.repository.OrderIncludesMenuRepository;
import com.obamaapi.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderIncludesMenuRepository orderIncludesMenuRepository;

    public boolean checkIfMenuNameExists(String menuName){
        if(menuRepository.findByMenuName(menuName) != null){
            return true;
        }else return false;
    }
    public void addMenu(AddMenuRequest addMenuRequest){
        MenuItems menuItem = new MenuItems();
        menuItem.setMenuName(addMenuRequest.getMenuName());
        menuItem.setPrice(addMenuRequest.getPrice());
        menuItem.setImageUrl(addMenuRequest.getImageUrl());
        menuItem.setAvailability(MenuAvailability.NOT_AVAILABLE);
        menuItem.setType(addMenuRequest.getMenuType());

        menuRepository.save(menuItem);
    }
    public List<MenuItems> getMenuList(){
        List<MenuItems> menuItemsList = menuRepository.findAll();
        return menuItemsList;
    }
    public void setAvailability(long menuId){
        MenuItems menu = menuRepository.findByMenuId(menuId);
        if(menu.getAvailability().equals(MenuAvailability.AVAILABLE)){
            menu.setAvailability(MenuAvailability.NOT_AVAILABLE);
        }else {
            menu.setAvailability(MenuAvailability.AVAILABLE);
        }
        menuRepository.save(menu);
    }

    public void placeOrder(AddOrderRequest addOrderRequest){
        OrderDetails orderDetails = new OrderDetails();
        CustomerDetails customerDetails;

        customerDetails = customerRepository.findByUserDetails_UserId(addOrderRequest.getUserId());
        orderDetails.setPlacementId(addOrderRequest.getPlacementId());
        orderDetails.setAmount(addOrderRequest.getAmount());
        orderDetails.setStatus(OrderStatus.PLACED);
        orderDetails.setCustomerDetails(customerDetails);
        orderRepository.save(orderDetails);
    }

    public void addOrderMenu(AddOrderMenuRequest addOrderMenuRequest){
        OrderDetails orderDetails = orderRepository.findByOrderId(addOrderMenuRequest.getOrderId());

        for (RequestMenuInstance requestMenuInstance : addOrderMenuRequest.getRequestMenuInstances()){

            MenuItems menuItems = menuRepository.findByMenuId(requestMenuInstance.getMenuId());

            OrderIncludesMenu orderMenu = new OrderIncludesMenu();
            orderMenu.setMenuItems(menuItems);
            orderMenu.setQuantity(requestMenuInstance.getQuantity());
            orderMenu.setOrderDetails(orderDetails);

            orderIncludesMenuRepository.save(orderMenu);
        }

    }
}
