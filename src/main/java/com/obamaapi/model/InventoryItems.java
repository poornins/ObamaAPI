package com.obamaapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.obamaapi.enums.InventoryUnits;
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
    private float quantity;

    @Column (nullable = false)
    private float reorderLevel;

    @Enumerated(EnumType.STRING)
    private InventoryUnits unit;

    @OneToMany(targetEntity = AddInventoryItems.class, mappedBy = "inventoryItems", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<AddInventoryItems> staffAddInventories;

    @OneToMany(targetEntity = RetrieveInvetory.class, mappedBy = "inventoryItems", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<RetrieveInvetory> staffRetrieveInvetories;
}
