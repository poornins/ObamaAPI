package com.obamaapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.obamaapi.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderDetails {

    @Id
    @GeneratedValue
    private long orderId;

    @Column(nullable = false)
    private int placementId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column (nullable = false)
    private String amount;

    @ManyToOne
    @JsonIgnore
    private CustomerDetails customerDetails;

    @OneToMany(targetEntity = OrderIncludesMenu.class, mappedBy = "orderDetails", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<OrderIncludesMenu> orderIncludesMenus;

    @OneToOne(targetEntity = PaymentDetails.class, mappedBy = "orderDetails", cascade = CascadeType.ALL)
    @JsonIgnore
    private PaymentDetails paymentDetails;
}
