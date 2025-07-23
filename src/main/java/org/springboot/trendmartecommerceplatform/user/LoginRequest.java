package org.springboot.trendmartecommerceplatform.user;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @Email
    @Column(unique = true)
    private String email;
    @NotBlank(message = "Please enter your password")
    private String password;
}
