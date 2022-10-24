package com.obamaapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.obamaapi.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserDetails {

    @Id
    @GeneratedValue
    private long userId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String contactNumber;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @OneToOne(targetEntity = CustomerDetails.class, mappedBy = "userDetails", cascade = CascadeType.ALL)
    @JsonIgnore
    private CustomerDetails customerDetails;

    @OneToOne(targetEntity = StaffDetails.class, mappedBy = "userDetails", cascade = CascadeType.ALL)
    @JsonIgnore
    private StaffDetails staffDetails;

}
