package org.springboot.trendmartecommerceplatform.cart;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@SecurityRequirement(name ="auth")
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody CartItemRequest request) {
        return cartService.addToCart(request);
    }

    @GetMapping("/items")
    public ResponseEntity<List<CartItemResponse>> getCartItems() {
        return cartService.getCartItems();
    }

    @GetMapping("/subtotal")
    public ResponseEntity<BigDecimal> getCartSubtotal() {
        BigDecimal subtotal = cartService.getCartSubtotal();
        return ResponseEntity.ok(subtotal);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<String> updateItemQuantity(
            @PathVariable Long itemId,
            @RequestParam Integer quantity
    ) {
        return cartService.updateQuantity(itemId, quantity);
    }

    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<String> removeItem(@PathVariable Long itemId) {
        return cartService.removeItem(itemId);
    }

    @DeleteMapping("/clear")
    @Operation(description = "Clear cart")
    public ResponseEntity<String> clearCart() {
        return cartService.clearCart();
    }
}
