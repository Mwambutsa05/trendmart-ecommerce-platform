package org.springboot.trendmartecommerceplatform.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springboot.trendmartecommerceplatform.config.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "https://trend-mart-frontend-blue.vercel.app"},  allowedHeaders = "*",
        allowCredentials = "true",
        maxAge = 3600 )
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final EmailService emailService;

    @GetMapping()
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
         userService.register(request);
        return ResponseEntity.ok("User registered. OTP sent to email.");
    }

    @PostMapping("/verify-otp")
        public ResponseEntity<String> verifyOtp (@RequestParam String email
            , @RequestParam String code){
            boolean verified = userService.verifyOtp(email, code);
            if (verified) {
                return ResponseEntity.ok("Account verified successfully.");
            }
            return ResponseEntity.badRequest().body("Invalid or expired OTP.");
        }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login (@RequestBody LoginRequest request){
            AuthResponse response = userService.login(request);
            return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-ADMIN")
    public ResponseEntity<String> createAdmin (@RequestBody RegisterRequest request){
            userService.createAdmin(request);
            return ResponseEntity.ok("✅ New admin created successfully!");
    }

}


