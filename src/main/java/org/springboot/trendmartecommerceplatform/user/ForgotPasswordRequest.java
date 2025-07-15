package org.springboot.trendmartecommerceplatform.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgotPasswordRequest {
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email must not be blank")
    private String email;


}
