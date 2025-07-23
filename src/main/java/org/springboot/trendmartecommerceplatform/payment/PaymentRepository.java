package org.springboot.trendmartecommerceplatform.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Method 1: Using Spring Data JPA query derivation
    Optional<Payment> findByTxRef(String txRef);

    // OR Method 2: Using custom JPQL query
    @Query("SELECT p FROM Payment p WHERE p.txRef = :txRef")
    Optional<Payment> findPaymentByTransactionReference(String txRef);

    // Method 3: If you need to check existence
    boolean existsByTxRef(String txRef);
}
