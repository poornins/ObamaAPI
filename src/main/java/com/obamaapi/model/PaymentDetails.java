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
public class PaymentDetails {

    @Id
    @GeneratedValue
    private long invoiceId;

    @Column(nullable = false)
    private String amount;

    @CreationTimestamp
    private Date date;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "order_id")
    private OrderDetails orderDetails;
}
