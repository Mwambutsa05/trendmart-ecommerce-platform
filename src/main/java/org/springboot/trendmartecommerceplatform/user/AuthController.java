package org.springboot.trendmartecommerceplatform.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springboot.trendmartecommerceplatform.config.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = userService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    // Admin registration endpoint (you can secure this or remove it)
    @PostMapping("/register/admin")
    public ResponseEntity<AuthResponse> registerAdmin(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = userService.registerAdmin(request);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/verify/send")
    public ResponseEntity<String> sendCode(@RequestParam String email) {
        emailService .sendVerificationCode(email);
        return ResponseEntity.ok("Verification code sent.");
    }
    @PostMapping("/verify/check")
    public ResponseEntity<String> checkCode(@RequestParam String email, @RequestParam String code) {
        boolean isValid = emailService.verifyOtp(email, code);
        return isValid ? ResponseEntity.ok("Verified!") : ResponseEntity.badRequest().body("Invalid or expired code.");
    }
}


