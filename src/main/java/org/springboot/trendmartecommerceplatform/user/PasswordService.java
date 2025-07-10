package org.springboot.trendmartecommerceplatform.user;

import lombok.RequiredArgsConstructor;
import org.springboot.trendmartecommerceplatform.config.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordService {

    private final UserRepository userRepository;
    private final JwtUtil jwtService;
    private final PasswordEncoder passwordEncoder;

    public void forgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(user.getEmail());

        String link = "http://localhost:8080/reset-password?token=" + token;

        System.out.println("Reset link: " + link);
    }

    public void resetPassword(ResetPasswordRequest request) {
        String email = jwtService.extractUsername(request.getToken());

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        if (!jwtService.isTokenValid(request.getToken())) {
            throw new RuntimeException("Token expired or invalid");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
