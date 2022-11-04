package com.obamaapi.repository;

import com.obamaapi.model.CustomerDetails;
import com.obamaapi.model.InventoryItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InventoryRepository extends JpaRepository<InventoryItems, Long> {

    InventoryItems findByItemName(String name);
   List<InventoryItems> findAllByItemNameContains(String query);
   List<InventoryItems> findAllByItemNameIsContaining(String query);
    InventoryItems findByItemId(Long id);
    InventoryItems findByItemIdAndQuantityIsLessThan(long itemId,float reorderLevel);

}
