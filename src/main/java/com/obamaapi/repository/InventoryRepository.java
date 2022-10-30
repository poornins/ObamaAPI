package com.obamaapi.repository;

import com.obamaapi.model.CustomerDetails;
import com.obamaapi.model.InventoryItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<InventoryItems, Long> {

    InventoryItems findByItemName(String name);
    InventoryItems findByItemId(Long id);

}
