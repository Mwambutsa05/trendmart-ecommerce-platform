package org.springboot.trendmartecommerceplatform.cart;

import lombok.RequiredArgsConstructor;
import org.springboot.trendmartecommerceplatform.Product.Product;
import org.springboot.trendmartecommerceplatform.Product.ProductRepository;
import org.springboot.trendmartecommerceplatform.exceptionHandling.ResourceNotFound;
import org.springboot.trendmartecommerceplatform.user.User;
import org.springboot.trendmartecommerceplatform.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    private final UserRepository userRepository;

    private final Long userId = 1L;

    private Cart getOrCreateCart() {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User not found"));

        return cartRepository.findByUser_Id(userId).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setCreatedAt(LocalDateTime.now());
            return cartRepository.save(cart);
        });
    }

    public ResponseEntity<String> addToCart(CartItemRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFound("Product not found"));

        Cart cart = getOrCreateCart();

        Optional<CartItem> existing = cartItemRepository.findByCart_IdAndProduct_Id(cart.getId(), product.getId());
        if (existing.isPresent()) {
            CartItem item = existing.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
            cartItemRepository.save(item);
        } else {
            CartItem item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(request.getQuantity());
            cartItemRepository.save(item);
        }

        return ResponseEntity.ok("Product added to cart successfully!");
    }

    public ResponseEntity<List<CartItemResponse>> getCartItems() {
        Cart cart = getOrCreateCart();

        List<CartItem> items = cartItemRepository.findByCart_Id(cart.getId());

        List<CartItemResponse> responses = items.stream().map(item -> {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new ResourceNotFound("Product not found"));

            CartItemResponse response = new CartItemResponse();
            response.setItemId(item.getId());
            response.setProductId(product.getId());
            response.setProductName(product.getName());
            response.setQuantity(item.getQuantity());
            response.setPrice(product.getPrice());

            if (product.getPrice() != null && item.getQuantity() != null) {
                response.setSubtotal(product.getPrice().multiply(new BigDecimal(item.getQuantity())));
            } else {
                response.setSubtotal(BigDecimal.ZERO);
            }

            return response;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<String> updateQuantity(Long itemId, Integer quantity) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFound("Cart item not found"));

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
        cartItemRepository.deleteAllByCart_Id(cart.getId());
        return ResponseEntity.ok("Cart cleared successfully.");
    }


    public BigDecimal getCartSubtotal() {
        Cart cart = getOrCreateCart();

        List<CartItem> items = cartItemRepository.findByCart_Id(cart.getId());

        return items.stream()
                .map(item -> {
                    if (item.getProduct() != null && item.getProduct().getPrice() != null && item.getQuantity() != null) {
                        return item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity()));
                    }
                    return BigDecimal.ZERO;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
