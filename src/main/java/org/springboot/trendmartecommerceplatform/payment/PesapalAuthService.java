package org.springboot.trendmartecommerceplatform.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PesapalAuthService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${pesapal.consumer-key}")
    private String consumerKey;

    @Value("${pesapal.consumer-secret}")
    private String consumerSecret;

    @Value("${pesapal.base-url}")
    private String baseUrl;

    private String cachedToken;

    public String getAccessToken() {
        if (cachedToken != null) {
            return cachedToken;
        }

        String url = baseUrl + "/Auth/RequestToken";

        Map<String, String> payload = new HashMap<>();
        payload.put("consumer_key", consumerKey);
        payload.put("consumer_secret", consumerSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        cachedToken = (String) response.getBody().get("token");

        return cachedToken;
    }
}

