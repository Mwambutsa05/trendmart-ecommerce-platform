package org.springboot.trendmartecommerceplatform.cart;
import lombok.Data;

@Data
public class CartItemRequest {
    private Long productId;
    private Integer quantity;
}

