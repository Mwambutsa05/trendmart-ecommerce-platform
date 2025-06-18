package org.springboot.trendmartecommerceplatform.user;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Data
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private Date dateOfBirth;
    private String role;
}
