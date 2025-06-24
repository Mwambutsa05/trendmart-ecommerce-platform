package org.springboot.trendmartecommerceplatform.review;

import jakarta.validation.constraints.*;
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
    @NotNull(message = "User ID is required")
    @Pattern(regexp = "\\d+", message = "ID must contain digits only")
    private Long userId;
    @NotNull(message = "product ID is required")
    @Pattern(regexp = "\\d+", message = "ID must contain digits only")
    private Long productId;
    @Positive(message = "Rating must be a positive")
    @Max(value = 5, message = "Rating must be less that or equal to 5")
    private final int rating;
    @NotEmpty
    private final String comment;

}