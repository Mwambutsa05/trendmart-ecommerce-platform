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
    @Size(max = 50, message ="your name should not exceed 50 characters" )
    private String firstName;
    @NotBlank(message = "Please enter your last name")
    @Size(max = 50, message ="your name should not exceed 50 characters" )
    private String lastName;
    @NotBlank(message = "Please enter your username")
    @Size(max = 50, message ="your username should not exceed 50 characters" )
    private String username;
    @Email
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    @NotBlank(message = "Please enter your Password")
    @Size(min = 4, message ="your passcode should atleast be above 4 characters" )
    private String password;
    private Date dateOfBirth;
}
