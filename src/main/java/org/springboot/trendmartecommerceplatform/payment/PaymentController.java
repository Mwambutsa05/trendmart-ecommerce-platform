package org.springboot.trendmartecommerceplatform.payment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "*")
public class PaymentController {

    private final FlutterwavePaymentService service;

    public PaymentController(FlutterwavePaymentService service) {
        this.service = service;
    }

    @PostMapping("/initiate")
    public ResponseEntity<PaymentResponseDto> initiate(@RequestBody PaymentRequestDto dto) {
        String txRef = "TXN_" + UUID.randomUUID().toString().substring(0, 8);
        String link = service.initiatePayment(dto, txRef);

        if (link == null) {
            return ResponseEntity.badRequest()
                    .body(new PaymentResponseDto("error", null, txRef, "Payment failed"));
        }

        return ResponseEntity.ok(
                new PaymentResponseDto("success", link, txRef, "Redirect to payment")
        );
    }

    @GetMapping("/callback")
    public ResponseEntity<?> callback(@RequestParam Map<String, String> params) {
        String txRef = params.get("tx_ref");
        String transactionId = params.get("transaction_id");
        String status = params.get("status");

        if (!"successful".equals(status)) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "failed",
                    "message", "Payment not successful"
            ));
        }

        service.storeSuccessfulPayment(
                txRef,
                transactionId,
                0,
                "RWF",
                "unknown@example.com",
                "Unknown",
                "User",
                "successful"
        );

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Payment saved",
                "txRef", txRef,
                "transactionId", transactionId
        ));
    }
}
