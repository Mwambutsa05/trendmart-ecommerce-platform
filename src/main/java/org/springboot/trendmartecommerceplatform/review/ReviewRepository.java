package org.springboot.trendmartecommerceplatform.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review>  findByProductId(Long productId);


}