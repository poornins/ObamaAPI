package com.obamaapi.service;

import com.obamaapi.dto.requests.AddMenuRequest;
import com.obamaapi.dto.requests.AddOrderMenuRequest;
import com.obamaapi.dto.requests.AddOrderRequest;
import com.obamaapi.dto.requests.MenuInstance;
import com.obamaapi.dto.responses.PlacedOrderResponse;
import com.obamaapi.enums.MenuAvailability;
import com.obamaapi.enums.OrderStatus;
import com.obamaapi.model.*;
import com.obamaapi.repository.*;
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
    private StaffRepository staffRepository;

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
                menuInstance.setId(orderIncludesMenu.getMenuItems().getMenuId());
                menuInstance.setName(orderIncludesMenu.getMenuItems().getMenuName());
                menuInstance.setQty(orderIncludesMenu.getQuantity());

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
                menuInstance.setId(orderIncludesMenu.getMenuItems().getMenuId());
                menuInstance.setName(orderIncludesMenu.getMenuItems().getMenuName());
                menuInstance.setQty(orderIncludesMenu.getQuantity());

                menuInstances.add(menuInstance);
            }
            placedOrderResponse.setMenuInstances(menuInstances);
            placedOrderResponseList.add(placedOrderResponse);
        }
        return placedOrderResponseList;
    }
    public void assignOrder(long userId,long orderId){
        OrderDetails order = orderRepository.findByOrderId(orderId);
        StaffDetails staff = staffRepository.findByUserDetails_UserId(userId);
        order.setStaffDetails(staff);
        orderRepository.save(order);
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

    public OrderDetails placeOrder(AddOrderRequest addOrderRequest){
        OrderDetails orderDetails = new OrderDetails();
        CustomerDetails customerDetails;

        //get customer details on user ID
        customerDetails = customerRepository.findByUserDetails_UserId(addOrderRequest.getUserId());

        orderDetails.setPlacementId(addOrderRequest.getPlacementId());
        orderDetails.setAmount(addOrderRequest.getAmount());
        orderDetails.setStatus(OrderStatus.PLACED);
        orderDetails.setCustomerDetails(customerDetails);
        //save in the database
       return orderRepository.save(orderDetails);

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

            MenuItems menuItems = menuRepository.findByMenuId(menuInstance.getId());

            OrderIncludesMenu orderMenu = new OrderIncludesMenu();
            orderMenu.setMenuItems(menuItems);
            orderMenu.setQuantity(menuInstance.getQty());
            orderMenu.setOrderDetails(orderDetails);

            orderIncludesMenuRepository.save(orderMenu);
        }

    }
}
