package org.springboot.trendmartecommerceplatform.payment;

import lombok.RequiredArgsConstructor;
import org.springboot.trendmartecommerceplatform.payment.PesapalOrderRequest;
import org.springboot.trendmartecommerceplatform.payment.PesapalOrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PesapalPaymentController {

    private final PesapalPaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<PesapalOrderResponse> createPayment(@RequestBody PesapalOrderRequest request) {
        PesapalOrderResponse response = paymentService.createOrder(request);
        return ResponseEntity.ok(response);
    }
}
