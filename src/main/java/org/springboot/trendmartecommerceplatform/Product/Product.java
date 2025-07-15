package org.springboot.trendmartecommerceplatform.Product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springboot.trendmartecommerceplatform.category.Category;
import org.springboot.trendmartecommerceplatform.discountAds.Discount;
import org.springboot.trendmartecommerceplatform.review.Review;
import org.springboot.trendmartecommerceplatform.wishlist.WishlistItem;
import org.springboot.trendmartecommerceplatform.stock.Stock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private String skuCode;
    private String brand;
    private String quantity;
    @ElementCollection
    private List<String> imageUrls;
    @CreationTimestamp
    private Date created_at;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @JsonBackReference
    private Category category;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "stock_id")
    private Stock stock;


    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Review> reviews = new ArrayList<>();

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "discount_id")
    private Discount discount;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<WishlistItem> wishlistItems = new ArrayList<>();



    public BigDecimal getDiscountPrice() {
        if(discount != null &&
        discount.getDiscountPercentage() > 0 &&
        discount.getStartDate()!= null &&
        discount.getEndDate()!= null) {
            LocalDate today = LocalDate.now();
            if (!today.isBefore(discount.getStartDate()) && !today.isAfter(discount.getEndDate())) {
                BigDecimal discountAmount = price.multiply(BigDecimal.valueOf(discount.getDiscountPercentage() / 100));
                return price.subtract(discountAmount);
             }
        }
        return price;
    }
}

