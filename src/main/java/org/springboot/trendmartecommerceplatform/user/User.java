package org.springboot.trendmartecommerceplatform.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import org.springboot.trendmartecommerceplatform.address.Address;
import org.springboot.trendmartecommerceplatform.review.Review;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Users")
public class User implements UserDetails {
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

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
  
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Review> review = new ArrayList<>();

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Address address;

}
