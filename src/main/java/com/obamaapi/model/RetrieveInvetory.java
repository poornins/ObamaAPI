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
public class RetrieveInvetory {

    @Id
    @GeneratedValue
    private long retreiveId;

    @CreationTimestamp
    private Date date;

    @Column(nullable = false)
    private float retrievedQuantity;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "itemId")
    InventoryItems inventoryItems;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "staffId")
    StaffDetails staffDetails;
}
