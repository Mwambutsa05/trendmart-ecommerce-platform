package org.springboot.trendmartecommerceplatform.wishlist;

import lombok.Data;
import java.util.List;

@Data
public class WishlistResponseDTO {
    private Long wishlistId;
    private Long userId;
    private List<WishlistItemDTO> items;
}
