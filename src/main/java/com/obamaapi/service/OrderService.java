package com.obamaapi.service;

import com.obamaapi.dto.requests.AddMenuRequest;
import com.obamaapi.dto.requests.AddOrderMenuRequest;
import com.obamaapi.dto.requests.AddOrderRequest;
import com.obamaapi.dto.requests.MenuInstance;
import com.obamaapi.dto.responses.PlacedOrderResponse;
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

import java.util.ArrayList;
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

    public List<PlacedOrderResponse> getPlacedOrders(){
        List<OrderDetails> orderDetailsList=orderRepository.findAllByStatus(OrderStatus.PLACED);
        List<PlacedOrderResponse> placedOrderResponseList=new ArrayList<>();

        for (OrderDetails order: orderDetailsList){
            PlacedOrderResponse placedOrderResponse=new PlacedOrderResponse();
            placedOrderResponse.setOrderId(order.getOrderId());
            List<OrderIncludesMenu> orderIncludesMenuList=orderIncludesMenuRepository.findAllByOrderDetails_OrderId(order.getOrderId());
            List<MenuInstance> menuInstances=new ArrayList<>();
            for (OrderIncludesMenu orderIncludesMenu:orderIncludesMenuList){
                MenuInstance menuInstance=new MenuInstance();
                menuInstance.setMenuId(orderIncludesMenu.getMenuItems().getMenuId());
                menuInstance.setMenuName(orderIncludesMenu.getMenuItems().getMenuName());
                menuInstance.setQuantity(orderIncludesMenu.getQuantity());

                menuInstances.add(menuInstance);
            }
            placedOrderResponse.setMenuInstances(menuInstances);
            placedOrderResponseList.add(placedOrderResponse);
        }
        return placedOrderResponseList;
    }

    public List<PlacedOrderResponse> getAccepted(){
        List<OrderDetails> orderDetailsList=orderRepository.findAllByStatusOrStatus(OrderStatus.ACCEPTED,OrderStatus.PREPARED);
        List<PlacedOrderResponse> placedOrderResponseList=new ArrayList<>();

        for (OrderDetails order: orderDetailsList){
            PlacedOrderResponse placedOrderResponse=new PlacedOrderResponse();
            placedOrderResponse.setOrderId(order.getOrderId());
            List<OrderIncludesMenu> orderIncludesMenuList=orderIncludesMenuRepository.findAllByOrderDetails_OrderId(order.getOrderId());
            List<MenuInstance> menuInstances=new ArrayList<>();
            for (OrderIncludesMenu orderIncludesMenu:orderIncludesMenuList){
                MenuInstance menuInstance=new MenuInstance();
                menuInstance.setMenuId(orderIncludesMenu.getMenuItems().getMenuId());
                menuInstance.setMenuName(orderIncludesMenu.getMenuItems().getMenuName());
                menuInstance.setQuantity(orderIncludesMenu.getQuantity());

                menuInstances.add(menuInstance);
            }
            placedOrderResponse.setMenuInstances(menuInstances);
            placedOrderResponseList.add(placedOrderResponse);
        }
        return placedOrderResponseList;
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

        //get customer details on user ID
        customerDetails = customerRepository.findByUserDetails_UserId(addOrderRequest.getUserId());

        orderDetails.setPlacementId(addOrderRequest.getPlacementId());
        orderDetails.setAmount(addOrderRequest.getAmount());
        orderDetails.setStatus(OrderStatus.PLACED);
        orderDetails.setCustomerDetails(customerDetails);
        //save in the database
        orderRepository.save(orderDetails);
    }

    public void acceptOrder(long orderId){
        OrderDetails orderDetails = orderRepository.findByOrderId(orderId);
        orderDetails.setStatus(OrderStatus.ACCEPTED);
        orderRepository.save(orderDetails);
    }

    public void prepareOrder(long orderId){
        OrderDetails orderDetails = orderRepository.findByOrderId(orderId);
        orderDetails.setStatus(OrderStatus.PREPARED);
        orderRepository.save(orderDetails);
    }

    public void addOrderMenu(AddOrderMenuRequest addOrderMenuRequest){
        OrderDetails orderDetails = orderRepository.findByOrderId(addOrderMenuRequest.getOrderId());

        for (MenuInstance menuInstance : addOrderMenuRequest.getMenuInstances()){

            MenuItems menuItems = menuRepository.findByMenuId(menuInstance.getMenuId());

            OrderIncludesMenu orderMenu = new OrderIncludesMenu();
            orderMenu.setMenuItems(menuItems);
            orderMenu.setQuantity(menuInstance.getQuantity());
            orderMenu.setOrderDetails(orderDetails);

            orderIncludesMenuRepository.save(orderMenu);
        }

    }
}
