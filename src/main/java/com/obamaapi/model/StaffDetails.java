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
public class StaffDetails {

    @Id
    @GeneratedValue
    private long staffId;

    @Column(nullable = false)
    private String availability;

    @Column(nullable = false)
    private String password;

    @OneToOne
    @JsonIgnore
    private UserDetails userDetails;

    @OneToMany(targetEntity = StaffAddInventory.class, mappedBy = "staffDetails", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<StaffAddInventory> staffAddInventories;

    @OneToMany(targetEntity = StaffRetrieveInvetory.class, mappedBy = "staffDetails", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<StaffRetrieveInvetory> staffRetrieveInvetories;
}
