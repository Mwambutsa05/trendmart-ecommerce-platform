package org.springboot.trendmartecommerceplatform.user;

import lombok.AllArgsConstructor;
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
                .orElseThrow(() -> new ResourceNotFound("User not found"));
        userRepository.delete(user);
        return user;
    }

    public User register(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new ResourceNotFound("Passwords do not match");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResourceNotFound("Email already exists");
        }

        User user = new User();
        user.setFirstName(request.getFullName());
        user.setFullName(request.getFullName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setConfirmPassword(request.getConfirmPassword());
        user.setRole(Role.USER);
        user.setVerified(false);
        user.setEnabled(false);

        userRepository.save(user);

        // Generate and send OTP
        String code = String.format("%06d", new Random().nextInt(999999));
        otpStore.put(user.getEmail(), code);
        emailService.sendVerificationCode(user.getEmail(), code);

        return user;
    }

    public boolean verifyOtp(String email, String code) {
        String storedOtp = otpStore.get(email);
        if (storedOtp != null && storedOtp.equals(code)) {
            User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFound("User not found"));
            user.setVerified(true);
            user.setEnabled(true);
            userRepository.save(user);
            otpStore.remove(email);
            return true;
        }
        return false;
    }

    public void setAdmin() {
        String adminEmail = "uwamahorosonia1@gmail.com";
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            User admin = new User();
            admin.setFirstName("sonia");
            admin.setLastName("uwamahorosonia");
            admin.setUsername("Default-admin");
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode("Rwanda123$#@"));
            admin.setRole(Role.ADMIN);
            admin.setEnabled(true);
            admin.setVerified(true);
            userRepository.save(admin);
            System.out.println("✅ Default admin created: " + adminEmail);
        } else {
            System.out.println("ℹ️ Default Admin already exists: " + adminEmail);
        }
    }

    public User createAdmin(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResourceNotFound("Email already exists");
        }

        User user = new User();
        user.setFirstName(request.getFullName());
        user.setFullName(request.getFullName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setConfirmPassword(request.getConfirmPassword());
        user.setRole(Role.ADMIN);
        user.setVerified(true);
        user.setEnabled(true);
        userRepository.save(user);

        String code = String.format("%06d", new Random().nextInt(999999));
        otpStore.put(user.getEmail(), code);
        emailService.sendVerificationCode(user.getEmail(), code);

        return user;
    }

    // ✅ Register Admin (separate logic from createAdmin if needed)
    public User registerAdmin(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new ResourceNotFound("Passwords do not match");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResourceNotFound("Email already exists");
        }

        User admin = new User();
        admin.setFirstName(request.getFullName());
        admin.setFullName(request.getFullName());
        admin.setUsername(request.getUsername());
        admin.setEmail(request.getEmail());
        admin.setPhoneNumber(request.getPhoneNumber());
        admin.setDateOfBirth(request.getDateOfBirth());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setConfirmPassword(request.getConfirmPassword());
        admin.setRole(Role.ADMIN);
        admin.setVerified(false);
        admin.setEnabled(false);

        userRepository.save(admin);

        String code = String.format("%06d", new Random().nextInt(999999));
        otpStore.put(admin.getEmail(), code);
        emailService.sendVerificationCode(admin.getEmail(), code);

        return admin;
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

        return new AuthResponse(
                token,
                user.getEmail(),
                user.getUsername(),
                user.getFirstName(),
                user.getRole().name(),
                user.getId()
        );
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFound("User not found: " + email));
    }
}
