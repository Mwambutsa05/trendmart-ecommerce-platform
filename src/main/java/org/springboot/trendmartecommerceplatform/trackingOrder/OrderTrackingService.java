package org.springboot.trendmartecommerceplatform.trackingOrder;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springboot.trendmartecommerceplatform.exceptionHandling.ResourceNotFound;
import org.springboot.trendmartecommerceplatform.order.Order;
import org.springboot.trendmartecommerceplatform.order.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderTrackingService {

    private OrderTrackingRepository orderTrackingRepository;
    private OrderRepository orderRepository;

    public Order updateOrderStatus(Long orderId, OrderStatus newStatus, String updatedBy, String location) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFound("Order not found"));
        order.setStatus(newStatus);
        orderRepository.save(order);

        OrderTracking event = OrderTracking
                .builder()
                .order(order)
                .status(newStatus)
                .location(location)
                .updatedBy(updatedBy)
                .build();

        orderTrackingRepository.save(event);

        return order;

    }

    public List<OrderTracking> getTrackingEvents(Long orderId) {
        return orderTrackingRepository.findByOrderIdOrderByTimestampDesc(orderId);
    }
}
