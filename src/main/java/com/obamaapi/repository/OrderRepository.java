package com.obamaapi.repository;


import com.obamaapi.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderDetails,Long> {
    OrderDetails findByOrderId (long orderId);
}
