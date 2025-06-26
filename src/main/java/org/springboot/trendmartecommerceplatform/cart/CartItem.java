package org.springboot.trendmartecommerceplatform.cart;

import jakarta.persistence.*;
import lombok.*;
import org.springboot.trendmartecommerceplatform.product.Product;

@Entity
@Data
@Table(name = "cartitems")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer quantity;
}
