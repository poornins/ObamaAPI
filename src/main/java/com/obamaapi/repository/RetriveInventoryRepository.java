package com.obamaapi.repository;

import com.obamaapi.model.StaffAddInventory;
import com.obamaapi.model.StaffRetrieveInvetory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RetriveInventoryRepository extends JpaRepository<StaffRetrieveInvetory, Long> {

}
