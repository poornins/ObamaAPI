package com.obamaapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AddInventoryItems {

    @Id
    @GeneratedValue
    private long addInventoryId;

    @CreationTimestamp
    private Date date;

    @Column(nullable = false)
    private float addedQuantity;

    @Column(nullable = false)
    private float unitPrice;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "item_id")
    InventoryItems inventoryItems;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "staff_id")
    StaffDetails staffDetails;

}
