package org.springboot.trendmartecommerceplatform.review;

import lombok.RequiredArgsConstructor;
import org.springboot.trendmartecommerceplatform.Product.Dto;
import org.springboot.trendmartecommerceplatform.Product.Product;
import org.springboot.trendmartecommerceplatform.Product.ProductRepository;
import org.springboot.trendmartecommerceplatform.exceptionHandling.ResourceNotFound;
import org.springboot.trendmartecommerceplatform.user.User;
import org.springboot.trendmartecommerceplatform.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public Review addReview(ReviewDto Dto) {

        User user = userRepository.findById(Dto.getUserId()).orElseThrow(() -> new ResourceNotFound("User not found"));
        Product product = productRepository.findById(Dto.getProductId()).orElseThrow(() -> new ResourceNotFound("Product not found"));
        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(Dto.getRating());
        review.setComment(Dto.getComment());
         return reviewRepository.save(review);

    }
    public List<Review> findAllReviews() {
        return reviewRepository.findAll();

    }
    public Review findReviewById(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Review not found"));

    }
    public Review findReviewByProductId(Long productId) {
        return reviewRepository.findByProductId(productId).orElseThrow(() -> new ResourceNotFound("Review not found"));

    }
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }











}
