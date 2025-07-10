package org.springboot.trendmartecommerceplatform.user;

import lombok.RequiredArgsConstructor;
import org.springboot.trendmartecommerceplatform.config.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PasswordService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public void forgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        String token = jwtUtil.generateToken(user.getEmail());
        String resetLink = "http://localhost:8080/reset-password?token=" + token;

        System.out.println(" Password reset link: " + resetLink);
    }

    public void resetPassword(ResetPasswordRequest request) {
        String email = jwtUtil.extractUserName(request.getToken());

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token or user"));

        if (!jwtUtil.isTokenValid(request.getToken())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token expired or invalid");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
