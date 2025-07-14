package org.springboot.trendmartecommerceplatform.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDto {
    private String status;
    private String paymentLink;
    private String txRef;
    private String message;
}
