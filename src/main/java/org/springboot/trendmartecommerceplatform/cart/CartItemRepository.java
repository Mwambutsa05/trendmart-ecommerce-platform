package org.springboot.trendmartecommerceplatform.cart;

import org.springboot.trendmartecommerceplatform.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCartId(Long Id);
}

