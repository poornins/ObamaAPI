package com.obamaapi.repository;


import com.obamaapi.enums.OrderStatus;
import com.obamaapi.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderDetails,Long> {
    OrderDetails findByOrderId (long orderId);
    List<OrderDetails> findAllByStatus(OrderStatus orderStatus);
    List<OrderDetails> findAllByStatusOrStatus(OrderStatus orderStatus,OrderStatus orderStatus2);
    List<OrderDetails> findAllByDate(Date date);

}
