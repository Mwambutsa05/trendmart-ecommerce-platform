package org.springboot.trendmartecommerceplatform.payment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsByTxRef(String txRef);
}
