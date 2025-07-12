package org.springboot.trendmartecommerceplatform.payment;

import lombok.Data;

@Data
public class PesapalOrderRequest {
    private String amount;
    private String currency = "RWF";
    private String description;
    private String callbackUrl;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
}
