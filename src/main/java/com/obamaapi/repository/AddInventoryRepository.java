package com.obamaapi.repository;

import com.obamaapi.model.AddInventoryItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddInventoryRepository extends JpaRepository<AddInventoryItems, Long> {

}
