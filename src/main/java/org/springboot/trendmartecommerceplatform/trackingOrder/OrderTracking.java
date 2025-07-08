package org.springboot.trendmartecommerceplatform.trackingOrder;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springboot.trendmartecommerceplatform.order.Order;

import java.nio.CharBuffer;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;
    private String description;
    private String location;
    private String updatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;


    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }
}
