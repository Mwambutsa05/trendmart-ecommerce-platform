package org.springboot.trendmartecommerceplatform.order;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springboot.trendmartecommerceplatform.order.OrderRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@SecurityRequirement(name = "auth")
public class OrderController {

    private final OrderService orderService;


    @PostMapping("/place")
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest request) {
        return orderService.placeOrder(request);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {
        return orderService.getOrdersByUser(userId);
    }
}