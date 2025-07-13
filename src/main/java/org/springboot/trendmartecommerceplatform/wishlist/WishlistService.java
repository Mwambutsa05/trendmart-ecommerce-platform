package org.springboot.trendmartecommerceplatform.wishlist;
import lombok.RequiredArgsConstructor;
import org.springboot.trendmartecommerceplatform.Product.Product;
import org.springboot.trendmartecommerceplatform.Product.ProductRepository;
import org.springboot.trendmartecommerceplatform.user.User;
import org.springboot.trendmartecommerceplatform.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;

    public void addToWishlist(User user, Long productId) {
        wishlistRepository.findByUserAndProductId(user, productId)
                .ifPresent(item -> { throw new RuntimeException("Product already in wishlist"); });

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        WishlistItem item = WishlistItem.builder()
                .user(user)
                .product(product)
                .build();

        wishlistRepository.save(item);
    }

    public List<WishlistItem> getWishlist(User user) {
        return wishlistRepository.findByUser(user);
    }

    public void removeFromWishlist(User user, Long productId) {
        wishlistRepository.deleteByUserAndProductId(user, productId);
    }
}
