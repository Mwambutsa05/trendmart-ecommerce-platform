package org.springboot.trendmartecommerceplatform.Product;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
public class Dto {
    @NotBlank(message = "Please enter your name")
    @Size(max = 50, message ="your name should not exceed 50 characters" )
    private String name;
    @NotBlank(message = "please enter the description")
    private String description;
    @NotBlank
    private BigDecimal price;
    @NotBlank
    private BigDecimal originalPrice;
    @NotBlank
    @Column(unique = true)
    private String skuCode;
    @NotBlank
    private String brand;
    @NotBlank
    private String quantity;
    @ElementCollection
    private List<String> imageUrls;

}





