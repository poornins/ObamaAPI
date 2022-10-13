package com.obamaapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class InventoryItems {
    @Id
    @GeneratedValue
    private long itemId;

    @Column (nullable = false)
    private String itemName;

    @Column (nullable = false)
    private int quantity;

    @OneToMany(targetEntity = StaffAddInventory.class, mappedBy = "inventoryItems", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<StaffAddInventory> staffAddInventories;

    @OneToMany(targetEntity = StaffRetrieveInvetory.class, mappedBy = "inventoryItems", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<StaffRetrieveInvetory> staffRetrieveInvetories;
}
