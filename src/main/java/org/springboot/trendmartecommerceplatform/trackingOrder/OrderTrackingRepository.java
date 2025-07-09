package org.springboot.trendmartecommerceplatform.trackingOrder;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderTrackingRepository extends JpaRepository<OrderTracking, Long> {
    List<OrderTracking> findByOrderIdOrderByTimestampDesc(Long orderId);
}
