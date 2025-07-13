package org.springboot.trendmartecommerceplatform.stock;

import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    @Override
    Optional<Stock> findById(Long id);
    Optional<Stock> findByProductId(Long id);
}
