package org.springboot.trendmartecommerceplatform.trackingOrder;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Dto {

    private OrderStatus status;
    private String location;
    private String updatedBy;
    @Column(nullable = false)
    private LocalDateTime timestamp;

}
