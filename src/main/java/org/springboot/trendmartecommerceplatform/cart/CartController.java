package org.springboot.trendmartecommerceplatform.cart;


import lombok.RequiredArgsConstructor;
import org.springboot.trendmartecommerceplatform.cart.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
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
}

