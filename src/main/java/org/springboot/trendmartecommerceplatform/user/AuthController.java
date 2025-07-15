package org.springboot.trendmartecommerceplatform.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springboot.trendmartecommerceplatform.config.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000",  allowCredentials = "true")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
         userService.register(request);
        return ResponseEntity.ok("User registered. OTP sent to email.");
    }
    // 2. Verify OTP
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestParam String email

            , @RequestParam String code) {
        boolean verified = userService.verifyOtp(email, code);
        if (verified) {
            return ResponseEntity.ok("Account verified successfully.");
        }
        return ResponseEntity.badRequest().body("Invalid or expired OTP.");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    // Admin registration endpoint (you can secure this or remove it)
    @PostMapping("/register/admin")
    public ResponseEntity<String> registerAdmin(@Valid @RequestBody RegisterRequest request) {
        userService.registerAdmin(request);
        return ResponseEntity.ok("Admin registered. OTP sent to email.");
    }
//    @PostMapping("/verify/send")
//    public ResponseEntity<String> sendCode(@RequestParam String email) {
//        emailService .sendVerificationCode(email);
//        return ResponseEntity.ok("Verification code sent.");
//    }
//    @PostMapping("/verify/check")
//    public ResponseEntity<String> checkCode(@RequestParam String email, @RequestParam String code) {
//        boolean isValid = emailService.verifyOtp(email, code);
//        return isValid ? ResponseEntity.ok("Verified!") : ResponseEntity.badRequest().body("Invalid or expired code.");
//    }
}


