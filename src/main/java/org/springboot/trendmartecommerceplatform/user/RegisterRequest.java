package org.springboot.trendmartecommerceplatform.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class RegisterRequest {
    @NotBlank(message = "Please enter your first name")
    private String FullName;
    @NotBlank(message = "Please enter your last name")
    private String username;
    @Email
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    @NotBlank(message = "Please enter your Password")
    private String password;
    @Transient
    private String confirmPassword;
    
    private Date dateOfBirth;
}
