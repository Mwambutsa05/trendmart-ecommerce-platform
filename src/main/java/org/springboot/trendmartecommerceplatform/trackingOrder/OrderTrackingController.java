package org.springboot.trendmartecommerceplatform.trackingOrder;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order-tracking")
@SecurityRequirement(name = "auth")
public class OrderTrackingController {

    private final OrderTrackingService orderTrackingService;

    @PatchMapping("/{orderId}")
    public ResponseEntity<?> updateStatus(@PathVariable long orderId, @RequestBody Dto dto) {
        try {
            return ResponseEntity.ok(orderTrackingService.updateOrderStatus(
                    orderId,
                    dto.getStatus(),
                    dto.getUpdatedBy()== null ? "ADMIN" : dto.getUpdatedBy(),
                    dto.getLocation()
                    ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("failed to update the order status " + e.getMessage());
        }
    }

    @GetMapping("/{orderId}/history")
    public ResponseEntity<?> getTrackingHistory(@PathVariable Long orderId) {
        try {
            List<OrderTracking> history = orderTrackingService.getTrackingEvents(orderId);
            return ResponseEntity.ok(history);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to fetch tracking history: " + e.getMessage());
        }
    }
}
