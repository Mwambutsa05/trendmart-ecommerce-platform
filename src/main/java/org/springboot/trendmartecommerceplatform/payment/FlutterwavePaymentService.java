package org.springboot.trendmartecommerceplatform.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class FlutterwavePaymentService {

    @Value("${flutterwave.secret-key}")
    private String secretKey;

    @Value("${flutterwave.base-url}")
    private String baseUrl;


    private final PaymentRepository paymentRepo;
    private final RestTemplate restTemplate = new RestTemplate();

    public FlutterwavePaymentService(PaymentRepository paymentRepo) {
        this.paymentRepo = paymentRepo;
    }

    public String initiatePayment(PaymentRequestDto dto, String txRef) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(secretKey); // = "Authorization: Bearer <secretKey>"

        Map<String, Object> body = new HashMap<>();
        body.put("tx_ref", txRef);
        body.put("amount", dto.getAmount());
        body.put("currency", dto.getCurrency());
        body.put("redirect_url", "http://localhost:8080/api/payment/callback");
        body.put("payment_options", "card,banktransfer,ussd");

        Map<String, String> customer = new HashMap<>();
        customer.put("email", dto.getEmail());
        customer.put("first_name", dto.getFirstName());
        customer.put("last_name", dto.getLastName());
        body.put("customer", customer);

        ResponseEntity<Map> res = restTemplate.postForEntity(
                baseUrl,
                new HttpEntity<>(body, headers),
                Map.class
        );

        if (res.getStatusCode() == HttpStatus.OK && res.getBody() != null) {
            Map<String, Object> data = (Map<String, Object>) res.getBody().get("data");
            return (String) data.get("link");
        }

        return null;
    }

    public void storeSuccessfulPayment(String txRef,
                                       String transactionId,
                                       int amount,
                                       String currency,
                                       String email,
                                       String firstName,
                                       String lastName,
                                       String status) {
        if (paymentRepo.existsByTxRef(txRef)) return;

        Payment payment = Payment.builder()
                .txRef(txRef)
                .transactionId(transactionId)
                .status("successful")
                .amount(amount)
                .currency(currency)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .status(status)
                .paymentTime(LocalDateTime.now())
                .build();

        paymentRepo.save(payment);
    }
}
