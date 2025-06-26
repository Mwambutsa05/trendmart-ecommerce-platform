package org.springboot.trendmartecommerceplatform.order;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    private Long userId;
    private Long addressId;
    private String paymentMethod;
    private List<OrderItemRequest> items;
}
