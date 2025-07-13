package org.springboot.trendmartecommerceplatform.wishlist;

import lombok.RequiredArgsConstructor;
import org.springboot.trendmartecommerceplatform.Product.Product;
import org.springboot.trendmartecommerceplatform.Product.ProductRepository;
import org.springboot.trendmartecommerceplatform.user.User;
import org.springboot.trendmartecommerceplatform.user.UserRepository;
import org.springboot.trendmartecommerceplatform.wishlist.WishlistRequestDTO;
import org.springboot.trendmartecommerceplatform.wishlist.WishlistItemDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    
    public void addToWishlist(WishlistRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        wishlistRepository.findByUserAndProduct(user, product)
                .ifPresent(i -> { throw new RuntimeException("Product already in wishlist"); });

        WishlistItem item = WishlistItem.builder()
                .user(user)
                .product(product)
                .build();

        wishlistRepository.save(item);
    }

    public List<WishlistItemDTO> getWishlist(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return wishlistRepository.findByUser(user)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public void removeFromWishlist(Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        wishlistRepository.deleteByUserAndProductId(user, productId);
    }

    private WishlistItemDTO toDTO(WishlistItem item) {
        WishlistItemDTO dto = new WishlistItemDTO();
        dto.setId(item.getId());
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setPrice(String.valueOf(item.getProduct().getPrice()));
        if (item.getProduct().getImageUrls() != null && !item.getProduct().getImageUrls().isEmpty()) {
            dto.setFirstImageUrl(item.getProduct().getImageUrls().get(0));
        }
        return dto;
    }
}
