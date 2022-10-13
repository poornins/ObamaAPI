package com.obamaapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StaffAddInventory {

    @Id
    @GeneratedValue
    private long addInventoryId;

    @Column
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-dd-MM")
    private Date date;

    @Column(nullable = false)
    private int addedQuantity;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "item_id")
    InventoryItems inventoryItems;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "staff_id")
    StaffDetails staffDetails;

}
