package org.springboot.trendmartecommerceplatform.wishlist;
import lombok.RequiredArgsConstructor;
import org.springboot.trendmartecommerceplatform.config.JwtUtil;
import org.springboot.trendmartecommerceplatform.user.User;
import org.springboot.trendmartecommerceplatform.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springboot.trendmartecommerceplatform.wishlist.WishlistRequestDTO;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService service;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody WishlistRequestDTO dto) {
        service.addToWishlist(dto);
        return ResponseEntity.ok("Added to wishlist");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<WishlistItemDTO>> get(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getWishlist(userId));
    }

    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<?> remove(@PathVariable Long userId,
                                    @PathVariable Long productId) {
        service.removeFromWishlist(userId, productId);
        return ResponseEntity.ok("Removed");
    }
}
