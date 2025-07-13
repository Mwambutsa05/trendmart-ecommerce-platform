package org.springboot.trendmartecommerceplatform.cart;

import org.springboot.trendmartecommerceplatform.user.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> items;

    public double getSubtotal() {
        if (items == null || items.isEmpty()) {
            return 0;
        }
        return items.stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
    }
}
