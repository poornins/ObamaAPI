package com.obamaapi.repository;

import com.obamaapi.model.OrderIncludesMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderIncludesMenuRepository extends JpaRepository<OrderIncludesMenu,Long> {
    List<OrderIncludesMenu> findAllByOrderDetails_OrderId(long orderId);
    @Query(value = "SELECT menu_id AS 'itemNo', SUM(quantity) AS 'quantity' FROM `order_includes_menu` WHERE DATE(date)= ?1 GROUP BY menu_id;" , nativeQuery = true)
    List<DailyInstance> findSumofMenuSales(String date);

    @Query(value = "SELECT menu_id AS 'itemNo', SUM(quantity) AS 'quantity' FROM `order_includes_menu` WHERE DATE(date) BETWEEN ?1 AND ?2 GROUP BY menu_id;" , nativeQuery = true)
    List<DailyInstance> findSumofMenuSalesPeriod(String fromDate,String toDate);

    @Query(value = "SELECT menu_id AS 'itemNo', SUM(quantity) AS 'quantity' FROM `order_includes_menu` GROUP BY menu_id ASC LIMIT 5" , nativeQuery = true)
    List<DailyInstance> findPopularMenus();

    interface DailyInstance{
        long getItemNo();
        int getQuantity();
    }

}

