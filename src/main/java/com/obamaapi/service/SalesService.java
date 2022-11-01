package com.obamaapi.service;

import com.obamaapi.dto.responses.DailySalesResponse;
import com.obamaapi.dto.responses.SalesInstance;
import com.obamaapi.dto.responses.SalesPeriodResponse;
import com.obamaapi.model.MenuItems;
import com.obamaapi.repository.CustomerRepository;
import com.obamaapi.repository.MenuRepository;
import com.obamaapi.repository.OrderIncludesMenuRepository;
import com.obamaapi.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalesService {
    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

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
            salesInstance.setTotal(dailyInstance.getQuantity()*Integer.parseInt(menuItem.getPrice()));
            salesInstance.setUnitPrice(menuItem.getPrice());
            salesInstanceList.add(salesInstance);
            total = total + dailyInstance.getQuantity()*Integer.parseInt(menuItem.getPrice());
        }
        salesPeriodResponse.setSalesInstances(salesInstanceList);
        salesPeriodResponse.setTotal(total);
        return salesPeriodResponse;
    }
}
