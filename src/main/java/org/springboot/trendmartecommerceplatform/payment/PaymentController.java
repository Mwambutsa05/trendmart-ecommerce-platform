package org.springboot.trendmartecommerceplatform.payment;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springboot.trendmartecommerceplatform.exceptionHandling.ResourceNotFound;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/payment")
@SecurityRequirement(name = "auth")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

    private final FlutterwavePaymentService service;
    private final PaymentRepository paymentRepo;

    public PaymentController(FlutterwavePaymentService service, PaymentRepository paymentRepo) {
        this.service = service;
        this.paymentRepo = paymentRepo;
    }

    @PostMapping("/initiate")
    public ResponseEntity<PaymentResponseDto> initiate(@RequestBody PaymentRequestDto dto) {

        if (dto.getEmail() == null || !dto.getEmail().contains("@") ||
                dto.getAmount() <= 0 || dto.getCurrency() == null) {
            return ResponseEntity.badRequest().body(
                    new PaymentResponseDto("error", null, null, "Invalid payment data")
            );
        }


        String txRef = "TXN_" + UUID.randomUUID().toString().substring(0, 8);

        try {
            // Step 3: Call service to create payment link
            String paymentLink = service.initiatePayment(dto, txRef);

            if (paymentLink == null) {
                return ResponseEntity.badRequest().body(
                        new PaymentResponseDto("error", null, txRef, "Payment initiation failed")
                );
            }

            // Step 4: Return payment link to frontend
            return ResponseEntity.ok(
                    new PaymentResponseDto("success", paymentLink, txRef, "Redirect to payment")
            );

        } catch (ResourceNotFound e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new PaymentResponseDto("error", null, txRef, e.getMessage())
            );
        }
    }


    @GetMapping("/callback")
    public ResponseEntity<Map<String, Object>> callback(@RequestParam Map<String, String> params) {


        String txRef = params.get("tx_ref");
        String transactionId = params.get("transaction_id");
        String status = params.get("status");


        if (txRef == null || transactionId == null || status == null) {
            return ResponseEntity.badRequest().body(
                    Map.of("status", "failed", "message", "Missing required parameters")
            );
        }

        try {

            boolean success = service.processPaymentCallback(txRef, transactionId, status);

            if (success) {

                Payment payment = paymentRepo.findByTxRef(txRef).orElseThrow();

                return ResponseEntity.ok(Map.of(
                        "status", "success",
                        "message", "Payment verified and saved",
                        "txRef", txRef,
                        "transactionId", transactionId,
                        "orderId", payment.getOrder() != null ? payment.getOrder().getId() : null
                ));
            }

            // Payment verification failed
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "failed",
                    "message", "Payment verification failed",
                    "txRef", txRef
            ));

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "error",
                    "message", "Payment processing error"
            ));
        }
    }
}