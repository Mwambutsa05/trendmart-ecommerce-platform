package org.springboot.trendmartecommerceplatform.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCart_Id(Long cartId);

    void deleteAllByCart_Id(Long cartId);

    Optional<CartItem> findByCart_IdAndProduct_Id(Long cartId, Long productId);

}
