package com.obamaapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CustomerDetails {
    @Id
    @GeneratedValue
    private long customerId;

    @Column(nullable = true)
    private String address;

    @CreationTimestamp
    private Date date;

    @Column(nullable = false)
    private String profileStatus;

    @OneToOne
    @JsonIgnore
    private UserDetails userDetails;

    @OneToMany(targetEntity = OrderDetails.class, mappedBy = "customerDetails", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<OrderDetails> orderDetails;
}
