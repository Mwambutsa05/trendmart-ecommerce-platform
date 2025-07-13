package org.springboot.trendmartecommerceplatform.wishlist;

import lombok.Data;

@Data
public class WishlistRequestDTO {
    private Long userId;
    private Long productId;
}
