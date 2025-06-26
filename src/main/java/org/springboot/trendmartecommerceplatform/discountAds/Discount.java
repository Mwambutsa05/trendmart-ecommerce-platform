package org.springboot.trendmartecommerceplatform.discountAds;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springboot.trendmartecommerceplatform.Product.Product;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "discount")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    private  Double discountPercentage = 0.0;
    private  Boolean active;

    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate endDate;

    @OneToOne(mappedBy = "discount",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Product product;


}
