package org.springboot.trendmartecommerceplatform.cart;
import lombok.RequiredArgsConstructor;
import org.springboot.trendmartecommerceplatform.product.Product;
import org.springboot.trendmartecommerceplatform.product.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    private final Long userId = 1L;

    private Cart getOrCreateCart() {
        return cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setCreatedAt(LocalDateTime.now());
            return cartRepository.save(cart);
        });
    }


    public ResponseEntity<String> addToCart(CartItemRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = getOrCreateCart();


        Optional<CartItem> existing = cartItemRepository.findByCartIdAndProductId((cart.getId().product.getId());
        if (existing.isPresent()) {
            CartItem item = existing.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
            cartItemRepository.save(item);
        } else {
            CartItem item = new CartItem();
            item.setCartId(cart.getId());
            item.setProduct(product.getId());
            item.setQuantity(request.getQuantity());
            cartItemRepository.save(item);
        }

        return ResponseEntity.ok("Product added to cart successfully!");
    }


    public ResponseEntity<List<CartItemResponse>> getCartItems() {
        Cart cart = getOrCreateCart();

        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());

        List<CartItemResponse> responses = items.stream().map(item -> {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            CartItemResponse response = new CartItemResponse();
            response.setItemId(item.getId());
            response.setProductId(product.getId());
            response.setProductName(product.getName());
            response.setQuantity(item.getQuantity());
            response.setPrice(product.getPrice());
            return response;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<String> updateQuantity(Long itemId, Integer quantity) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        item.setQuantity(quantity);
        cartItemRepository.save(item);

        return ResponseEntity.ok("Quantity updated successfully.");
    }


    public ResponseEntity<String> removeItem(Long itemId) {
        if (!cartItemRepository.existsById(itemId)) {
            return ResponseEntity.badRequest().body("Item not found.");
        }

        cartItemRepository.deleteById(itemId);
        return ResponseEntity.ok("Item removed from cart.");
    }


    public ResponseEntity<String> clearCart() {
        Cart cart = getOrCreateCart();
        cartItemRepository.deleteAllByCartId(cart.getId());
        return ResponseEntity.ok("Cart cleared successfully.");
    }
}
