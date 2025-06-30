package org.springboot.trendmartecommerceplatform.stock;


import lombok.*;

import java.time.LocalDateTime;

@Data
public class Dto {
    private String name;
    private String description;
    private String category;
    private int quantity;
    private LocalDateTime createdAt;
    private Long productId;

}

