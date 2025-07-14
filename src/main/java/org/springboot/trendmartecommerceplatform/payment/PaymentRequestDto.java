package org.springboot.trendmartecommerceplatform.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDto {
    private String email;
    private String firstName;
    private String lastName;
    private int    amount;
    private String currency;
}
