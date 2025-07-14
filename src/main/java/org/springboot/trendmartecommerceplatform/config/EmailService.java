package org.springboot.trendmartecommerceplatform.config;

import lombok.RequiredArgsConstructor;

import org.springboot.trendmartecommerceplatform.user.OtpVerification;
import org.springboot.trendmartecommerceplatform.user.VerificationCodeRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Component
@RequiredArgsConstructor

public class EmailService {
    private final VerificationCodeRepository verificationCodeRepository;

    private final JavaMailSender javaMailSender;

    // generate and send code
    public void sendVerificationCode(String email,String code) {
        OtpVerification verification = new OtpVerification();
        verification.setCode(code);
        verification.setEmail(email);
        verification.setExpiryDate(LocalDateTime.now().plusMinutes(10));
        verification.setUsed(false);
        verificationCodeRepository.save(verification);
//        send email

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Verification Code");
        message.setText("Your verification code is " + code);
        javaMailSender.send(message);
    }
//    public boolean verifyOtp(String email, String code) {
//        Optional<OtpVerification> optional = verificationCodeRepository.findByEmailAndCodeAndUsedFalse(email, code);
//        if (optional.isPresent()) {
//            OtpVerification verification = optional.get();
//            if (verification.getExpiryDate().isAfter(LocalDateTime.now())) {
//                verification.setUsed(true);
//                verificationCodeRepository.save(verification);
//                return true;
//            }
//        }
//        return false;
//    }

}
