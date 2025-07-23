package org.springboot.trendmartecommerceplatform.payment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springboot.trendmartecommerceplatform.order.Order;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String txRef;
    private String transactionId;
    private String status;
    private int amount;
    private String currency;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDateTime paymentTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
}
