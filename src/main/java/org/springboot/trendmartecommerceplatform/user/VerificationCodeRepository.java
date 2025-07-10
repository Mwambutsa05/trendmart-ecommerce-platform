package org.springboot.trendmartecommerceplatform.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationCodeRepository extends JpaRepository<OtpVerification, Long> {
    Optional<OtpVerification> findByEmailAndCodeAndUsedFalse(String email, String code);
}
