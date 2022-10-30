package com.obamaapi.repository;

import com.obamaapi.model.InventoryItems;
import com.obamaapi.model.StaffAddInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddInventoryRepository extends JpaRepository<StaffAddInventory, Long> {

}
