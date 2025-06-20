package org.springboot.trendmartecommerceplatform.Product;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Getter
@Setter
public class Dto {
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
}
