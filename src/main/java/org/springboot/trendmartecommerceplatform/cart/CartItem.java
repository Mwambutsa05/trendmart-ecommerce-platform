package org.springboot.trendmartecommerceplatform.cart;
import  org.springboot.trendmartecommerceplatform.cart.CartItem;

import jakarta.persistence.*;
import lombok.*;
import org.springboot.trendmartecommerceplatform.Product.Product;

@Entity
@Table(name = "cartitems")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer quantity;

    public double getSubtotal() {
        if (product == null || product.getPrice() == null || quantity == null) {
            return 0;
        }
        return product.getPrice() * quantity;
    }
}
