package org.springboot.trendmartecommerceplatform.discountAds;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link org.springboot.trendmartecommerceplatform.discountAds.Discount}
 */
@AllArgsConstructor
@Getter
@ToString
@Data
public class DiscountDto {
    @NotEmpty(message = "Title is required")
    private  String title;
    private  String description;

    @NotNull(message = "Discount percent is required")
    private Double discountPercentage;


    @NotNull(message = "Active status is required")
    private  Boolean active;
    @NotNull(message = "Product ID is required")
    private Long productId;
    @Column(nullable = true)
    private  LocalDate startDate;
    @Column(nullable = true)
    private  LocalDate endDate;
}