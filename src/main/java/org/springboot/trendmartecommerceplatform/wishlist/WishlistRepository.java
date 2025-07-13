package org.springboot.trendmartecommerceplatform.wishlist;
import  org.springboot.trendmartecommerceplatform.Product.Product;
import org.springboot.trendmartecommerceplatform.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<WishlistItem, Long> {
    List<WishlistItem> findByUser(User user);
    Optional<WishlistItem> findByUserAndProductId(User user, Long productId);
    void deleteByUserAndProductId(User user, Long productId);
}

