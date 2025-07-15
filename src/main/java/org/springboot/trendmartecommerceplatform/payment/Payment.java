package org.springboot.trendmartecommerceplatform.payment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "payments" )
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String transactionId;
    private String txRef;
    private String status;
    private int amount;
    private String currency;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDateTime paymentTime;


}
