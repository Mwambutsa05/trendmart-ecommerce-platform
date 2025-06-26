package org.springboot.trendmartecommerceplatform.order;

import jakarta.persistence.*;
import lombok.*;
import org.springboot.trendmartecommerceplatform.user.User;
import org.springboot.trendmartecommerceplatform.address.Address;
import org.springboot.trendmartecommerceplatform.order.OrderItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private BigDecimal totalAmount;

    private String status;

    private String paymentMethod;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;
}
