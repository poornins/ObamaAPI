package com.obamaapi.service;

import com.obamaapi.dto.responses.*;
import com.obamaapi.model.InventoryItems;
import com.obamaapi.model.MenuItems;
import com.obamaapi.model.RetrieveInvetory;
import com.obamaapi.repository.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SalesService {
    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private RetriveInventoryRepository retriveInventoryRepository;

    @Autowired
    private OrderIncludesMenuRepository orderIncludesMenuRepository;

    public DailySalesResponse getDailySales(String date){
        int total = 0;
        // create sales response
        DailySalesResponse dailySalesResponse = new DailySalesResponse();
        dailySalesResponse.setDate(date);
        List<SalesInstance> salesInstanceList = new ArrayList<>();

           List<OrderIncludesMenuRepository.DailyInstance> dailyInstances = orderIncludesMenuRepository.findSumofMenuSales(date);
            for (OrderIncludesMenuRepository.DailyInstance dailyInstance: dailyInstances){
                SalesInstance salesInstance = new SalesInstance();

                MenuItems menuItem = menuRepository.findByMenuId(dailyInstance.getItemNo());
                salesInstance.setItemNo(dailyInstance.getItemNo());
                salesInstance.setQuantity(dailyInstance.getQuantity());
                salesInstance.setMenuName(menuItem.getMenuName());
                salesInstance.setTotal(dailyInstance.getQuantity()*Integer.parseInt(menuItem.getPrice()));
                salesInstance.setUnitPrice(menuItem.getPrice());
                salesInstanceList.add(salesInstance);
                total = total + dailyInstance.getQuantity()*Integer.parseInt(menuItem.getPrice());
            }
            dailySalesResponse.setSalesInstances(salesInstanceList);
            dailySalesResponse.setTotal(total);
        return dailySalesResponse;
    }

    public SalesPeriodResponse getSalesPeriod(String fromDate,String toDate){
        int total = 0;
        // create sales response
        SalesPeriodResponse salesPeriodResponse = new SalesPeriodResponse();
        salesPeriodResponse.setFromDate(fromDate);
        salesPeriodResponse.setToDate(toDate);
        List<SalesInstance> salesInstanceList = new ArrayList<>();

        List<OrderIncludesMenuRepository.DailyInstance> dailyInstances = orderIncludesMenuRepository.findSumofMenuSalesPeriod(fromDate, toDate);
        for (OrderIncludesMenuRepository.DailyInstance dailyInstance: dailyInstances){
            SalesInstance salesInstance = new SalesInstance();

            MenuItems menuItem = menuRepository.findByMenuId(dailyInstance.getItemNo());
            salesInstance.setItemNo(dailyInstance.getItemNo());
            salesInstance.setQuantity(dailyInstance.getQuantity());
            salesInstance.setMenuName(menuItem.getMenuName());
            salesInstance.setTotal(dailyInstance.getQuantity() *Integer.parseInt(menuItem.getPrice()));
            salesInstance.setUnitPrice(menuItem.getPrice());
            salesInstanceList.add(salesInstance);
            total = total + dailyInstance.getQuantity()*Integer.parseInt(menuItem.getPrice());
        }
        salesPeriodResponse.setSalesInstances(salesInstanceList);
        salesPeriodResponse.setTotal(total);
        return salesPeriodResponse;
    }

    public DailyStoresResponse getDailyStores(){
       //get time and date
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        System.out.println(today);
        // create stores response
        DailyStoresResponse dailyStoresResponse = new DailyStoresResponse();
        dailyStoresResponse.setDate(today.toString());
        dailyStoresResponse.setTime(now.toString());
        List<StoresInstance> storesInstances = new ArrayList<>();

        //get all inventory items
        List<InventoryItems> inventoryItems = inventoryRepository.findAll();
        for (InventoryItems items: inventoryItems){
            StoresInstance storesInstance = new StoresInstance();
            System.out.println(items.getItemId());
            float retQty = 0;
            try {
                retQty  = retriveInventoryRepository.findRetItems(today.toString(),items.getItemId());
            }catch (Exception e){
                retQty=0;
            }

            storesInstance.setItemName(items.getItemName());
            storesInstance.setItemCode(items.getItemId());
            storesInstance.setQtyAvailable(items.getQuantity());
            storesInstance.setQtyUsed(retQty);

            storesInstances.add(storesInstance);
        }
        dailyStoresResponse.setStoresInstances(storesInstances);
        return dailyStoresResponse;
    }

    public List<PopularMenuResponse> getPopularMenuCounts(){

        //get
        List<OrderIncludesMenuRepository.DailyInstance> dailyInstances = orderIncludesMenuRepository.findPopularMenus();
        List<PopularMenuResponse> popularMenuResponses = new ArrayList<>();
        for (OrderIncludesMenuRepository.DailyInstance instance: dailyInstances){
            PopularMenuResponse popularMenuResponse = new PopularMenuResponse();
            popularMenuResponse.setLabel(menuRepository.findByMenuId(instance.getItemNo()).getMenuName());
            popularMenuResponse.setValue(instance.getQuantity());
            popularMenuResponses.add(popularMenuResponse);
        }

        return popularMenuResponses;
    }
}
