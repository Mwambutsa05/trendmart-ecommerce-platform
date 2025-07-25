package org.springboot.trendmartecommerceplatform.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String email;
    private String username;
    private String fullName;
    private String role;
    private Long userId;
}
