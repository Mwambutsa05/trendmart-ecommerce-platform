package org.springboot.trendmartecommerceplatform.user;

import lombok.AllArgsConstructor;
import org.springboot.trendmartecommerceplatform.Product.Product;
import org.springboot.trendmartecommerceplatform.config.EmailService;
import org.springboot.trendmartecommerceplatform.config.JwtUtil;
import org.springboot.trendmartecommerceplatform.exceptionHandling.ResourceNotFound;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final Map<String, String> otpStore = new HashMap<>();
    private final EmailService emailService;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User deleteUser(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Product not found"));
        userRepository.delete(user);
        return user;
    }

    public User register(RegisterRequest request) {
        if (!request.getPassword().equals(request.getPassword())) {
            throw new ResourceNotFound("Passwords do not match");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResourceNotFound("Email already exists");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setConfirmPassword(request.getConfirmPassword());
        user.setRole(Role.USER);
        user.setVerified(false);
        userRepository.save(user);
        String code = String.format("%06d", new Random().nextInt(999999));
        otpStore.put(user.getEmail(), code);
        emailService.sendVerificationCode(user.getEmail(), code);

        return user;

    }


    public boolean verifyOtp(String email, String code) {
        String storedOtp = otpStore.get(email);
        if (storedOtp != null && storedOtp.equals(code)) {
            User user = userRepository.findByEmail(email).orElseThrow();
            user.setVerified(true);
            user.setEnabled(true);
            userRepository.save(user);
            otpStore.remove(email);
            return true;
        }
        return false;
    }

    public User registerAdmin(RegisterRequest request) {

        if (!request.getPassword().equals(request.getPassword())) {
            throw new ResourceNotFound("Passwords do not match");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResourceNotFound("Email already exists");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setConfirmPassword(request.getConfirmPassword());
        user.setRole(Role.ADMIN);

         userRepository.save(user);

        String code = String.format("%06d", new Random().nextInt(999999));
        otpStore.put(user.getEmail(), code);
        emailService.sendVerificationCode(user.getEmail(), code);

        return user;


    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFound("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResourceNotFound("Wrong password");
        }

        if (!user.isVerified()) {
            throw new ResourceNotFound("Account not verified");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(token, user.getEmail(), user.getUsername(),
                user.getFullName(), user.getRole().name(), user.getId());
    }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFound("User not found: " + email));
    }

}