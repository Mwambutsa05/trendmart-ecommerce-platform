package org.springboot.trendmartecommerceplatform.wishlist;
import lombok.RequiredArgsConstructor;
import org.springboot.trendmartecommerceplatform.config.JwtUtil;
import org.springboot.trendmartecommerceplatform.user.User;
import org.springboot.trendmartecommerceplatform.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @PostMapping("/add/{productId}")
    public ResponseEntity<?> addToWishlist(@PathVariable Long productId, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        wishlistService.addToWishlist(user, productId);
        return ResponseEntity.ok("Added to wishlist");
    }

    @GetMapping
    public ResponseEntity<List<WishlistItem>> getWishlist(Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        return ResponseEntity.ok(wishlistService.getWishlist(user));
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<?> removeFromWishlist(@PathVariable Long productId, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        wishlistService.removeFromWishlist(user, productId);
        return ResponseEntity.ok("Removed from wishlist");
    }
}
