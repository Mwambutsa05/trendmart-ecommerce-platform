package org.springboot.trendmartecommerceplatform.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;


    @PostMapping("/api/cart/add")
    public ResponseEntity<String> addToCart(@RequestBody CartItemRequest request) {
        return cartService.addToCart(request);
    }


    @GetMapping("/api/cart/items")
    public ResponseEntity<List<CartItemResponse>> getCartItems() {
        return cartService.getCartItems();
    }


    @PutMapping("/api/cart/items/{itemId}")
    public ResponseEntity<String> updateItemQuantity(
            @PathVariable Long itemId,
            @RequestParam Integer quantity
    ) {
        return cartService.updateQuantity(itemId, quantity);
    }


    @DeleteMapping("/api/cart/items/{itemId}")
    public ResponseEntity<String> removeItem(@PathVariable Long itemId) {
        return cartService.removeItem(itemId);
    }



    @DeleteMapping("/api/cart/clear")
    public ResponseEntity<String> clearCart() {
        return cartService.clearCart();
    }
}
