package org.springboot.trendmartecommerceplatform.order;

import lombok.Data;

@Data
public class OrderItemRequest {
    private Long productId;
    private Integer quantity;
}

