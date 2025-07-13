package org.springboot.trendmartecommerceplatform.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class RegisterRequest {
    @NotBlank(message = "Please enter your first name")
    private String firstName;
    @NotBlank(message = "Please enter your last name")
    private String lastName;
    @NotBlank(message = "Please enter your username")
    private String username;
    @Email
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    @NotBlank(message = "Please enter your Password")
    private String password;
    private Date dateOfBirth;
}
