package org.springboot.trendmartecommerceplatform.review;

import lombok.RequiredArgsConstructor;
import org.springboot.trendmartecommerceplatform.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
//    private final ProductRepository productRepository;

}
