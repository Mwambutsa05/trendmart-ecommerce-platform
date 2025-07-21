package org.springboot.trendmartecommerceplatform.payment;

import lombok.RequiredArgsConstructor;
import org.springboot.trendmartecommerceplatform.exceptionHandling.ResourceNotFound;
import org.springboot.trendmartecommerceplatform.order.Order;
import org.springboot.trendmartecommerceplatform.order.OrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FlutterwavePaymentService {


    @Value("${flutterwave.secret-key}")
    private String secretKey;

    @Value("${flutterwave.base-url}")
    private String baseUrl;

    private final PaymentRepository paymentRepo;
    private final RestTemplate restTemplate;
    private final OrderRepository orderRepo;

    public String initiatePayment(PaymentRequestDto dto, String txRef) throws ResourceNotFound {
        try {

            HttpHeaders headers = createHeaders();

            Map<String, Object> paymentData = createPaymentBody(dto, txRef);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(paymentData, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    baseUrl + "/payments",
                    request,
                    Map.class
            );


            if (response.getStatusCode() == HttpStatus.OK &&
                    response.getBody() != null &&
                    response.getBody().get("status").equals("success")) {

                Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
                return (String) data.get("link");
            }

            // If request failed, throw error
            throw new ResourceNotFound("Failed to initiate payment: " +
                    (response.getBody() != null ? response.getBody().get("message") : "No response"));
        } catch (RestClientException e) {
            throw new ResourceNotFound("Payment service unavailable: " + e.getMessage());
        }
    }


    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(secretKey); // Add "Bearer " + secretKey to Authorization header
        return headers;
    }


    private Map<String, Object> createPaymentBody(PaymentRequestDto dto, String txRef) {
        Map<String, Object> body = new HashMap<>();

        body.put("tx_ref", txRef);
        body.put("amount", dto.getAmount());
        body.put("currency", dto.getCurrency());
        body.put("redirect_url", "http://yourdomain.com/api/payment/callback");
        body.put("payment_options", "card, banktransfer");


        Map<String, String> customer = new HashMap<>();
        customer.put("email", dto.getEmail());
        customer.put("name", dto.getFirstName() + " " + dto.getLastName());
        body.put("customer", customer);

        // Custom metadata (to link payment with order)
        Map<String, Object> meta = new HashMap<>();
        if (dto.getOrderId() != null) {
            meta.put("order_id", dto.getOrderId());
        }
        body.put("meta", meta);

        return body;
    }


    public Map<String, Object> verifyTransaction(String transactionId) throws ResourceNotFound {
        try {
            HttpHeaders headers = createHeaders();
            HttpEntity<String> request = new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    baseUrl + "/transactions/" + transactionId + "/verify",
                    HttpMethod.GET,
                    request,
                    Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody();
            }

            throw new ResourceNotFound("Transaction verification failed");
        } catch (RestClientException e) {
            throw new ResourceNotFound("Verification service error: " + e.getMessage());
        }
    }

    public boolean processPaymentCallback(String txRef, String transactionId, String status)
            throws ResourceNotFound {


        if (paymentRepo.existsByTxRef(txRef)) {
            return false;
        }

        if (!"successful".equalsIgnoreCase(status)) {
            return false;
        }

        Map<String, Object> verification = verifyTransaction(transactionId);
        if (verification == null || !verification.containsKey("data")) {
            throw new ResourceNotFound("Verification failed for transaction: " + transactionId);
        }

        Map<String, Object> data = (Map<String, Object>) verification.get("data");
        if (!"successful".equalsIgnoreCase(data.get("status").toString())) {
            return false;
        }

        try {

            Map<String, Object> customer = (Map<String, Object>) data.get("customer");
            Number amount = (Number) data.get("amount");

            Payment payment = Payment.builder()
                    .txRef(txRef)
                    .transactionId(transactionId)
                    .status("successful")
                    .amount(amount.intValue())
                    .currency(data.get("currency").toString())
                    .email(customer.get("email").toString())
                    .firstName(customer.get("first_name").toString())
                    .lastName(customer.get("last_name").toString())
                    .paymentTime(LocalDateTime.now())
                    .build();

            if (data.containsKey("meta") && ((Map)data.get("meta")).containsKey("order_id")) {
                Long orderId = Long.parseLong(((Map)data.get("meta")).get("order_id").toString());
                Order order = orderRepo.findById(orderId)
                        .orElseThrow(() -> new ResourceNotFound("Order not found"));
                payment.setOrder(order);
            }

            paymentRepo.save(payment);
            return true;
        } catch (Exception e) {
            throw new ResourceNotFound("Error saving payment: " + e.getMessage());
        }
    }
}