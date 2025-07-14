package org.springboot.trendmartecommerceplatform.payment;


import lombok.RequiredArgsConstructor;
import org.springboot.trendmartecommerceplatform.payment.PesapalOrderRequest;
import org.springboot.trendmartecommerceplatform.payment.PesapalOrderResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PesapalPaymentService {

    private final PesapalAuthService authService;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${pesapal.base-url}")
    private String baseUrl;

    public PesapalOrderResponse createOrder(PesapalOrderRequest orderRequest) {
        String token = authService.getAccessToken();

        String url = baseUrl + "/Transactions/SubmitOrderRequest";

        Map<String, Object> body = new HashMap<>();
        body.put("amount", orderRequest.getAmount());
        body.put("currency", orderRequest.getCurrency());
        body.put("description", orderRequest.getDescription());
        body.put("callback_url", orderRequest.getCallbackUrl());

        Map<String, String> customer = new HashMap<>();
        customer.put("email_address", orderRequest.getEmail());
        customer.put("phone_number", orderRequest.getPhoneNumber());
        customer.put("first_name", orderRequest.getFirstName());
        customer.put("last_name", orderRequest.getLastName());

        body.put("customer", customer);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        String redirectUrl = (String) response.getBody().get("redirect_url");

        PesapalOrderResponse orderResponse = new PesapalOrderResponse();
        orderResponse.setRedirectUrl(redirectUrl);

        return orderResponse;
    }
}
