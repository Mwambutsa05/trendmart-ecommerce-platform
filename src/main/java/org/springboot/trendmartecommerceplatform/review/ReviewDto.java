package org.springboot.trendmartecommerceplatform.review;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * DTO for {@link org.springboot.trendmartecommerceplatform.review.Review}
 *
 */
@AllArgsConstructor
@Data
@ToString
public class ReviewDto  {
    private Long userId;
    private Long productId;
    @Positive
    private final int rating;
    @NotEmpty
    private final String comment;

}