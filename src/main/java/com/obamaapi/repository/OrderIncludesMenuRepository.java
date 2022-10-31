package com.obamaapi.repository;

import com.obamaapi.model.OrderIncludesMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderIncludesMenuRepository extends JpaRepository<OrderIncludesMenu,Long> {
    List<OrderIncludesMenu> findAllByOrderDetails_OrderId(long orderId);

}
