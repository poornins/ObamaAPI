package com.obamaapi.repository;

import com.obamaapi.model.RetrieveInvetory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RetriveInventoryRepository extends JpaRepository<RetrieveInvetory, Long> {

    @Query(value = "SELECT SUM(retrieved_quantity) AS 'Quantity' FROM `retrieve_invetory` WHERE DATE(date)=?1 AND item_id=?2" , nativeQuery = true)
    float findRetItems(String date,long itemId);

}
