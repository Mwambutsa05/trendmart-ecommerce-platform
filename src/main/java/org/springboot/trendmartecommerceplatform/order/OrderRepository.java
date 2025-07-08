package org.springboot.trendmartecommerceplatform.order;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

    @Query("SELECT o FROM Order o JOIN FETCH o.trackingEvents WHERE o.id = :orderId")
    Optional<Order> findByIdWithTrackingEvents(Long orderId);
}

