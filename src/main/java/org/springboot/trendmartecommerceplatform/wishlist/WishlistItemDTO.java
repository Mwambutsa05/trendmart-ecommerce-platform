package org.springboot.trendmartecommerceplatform.wishlist;
import lombok.Data;

@Data
public class WishlistItemDTO {
    private Long id;
    private Long productId;
    private String productName;
    private String imageUrl;
    private String price;

    public void setFirstImageUrl(String s) {
    }
}

