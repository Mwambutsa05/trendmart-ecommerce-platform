package org.springboot.trendmartecommerceplatform.order;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;    // Instead of @ManyToOne
    private Long productId;  // Instead of @ManyToOne

    private Integer quantity;

    private BigDecimal price;

    public void setOrder(Order order) {
    }
}

