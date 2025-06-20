package org.springboot.trendmartecommerceplatform.review;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springboot.trendmartecommerceplatform.Product.Product;
import org.springboot.trendmartecommerceplatform.user.User;


import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue
    private Long id;
    private int rating;
    private String comment;
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @JoinColumn(name = "user_id")
    @JsonBackReference
    @ManyToOne
    private User user;


    @JsonBackReference
    @JoinColumn(name="product_id")
    @ManyToOne
    private Product product;
}
