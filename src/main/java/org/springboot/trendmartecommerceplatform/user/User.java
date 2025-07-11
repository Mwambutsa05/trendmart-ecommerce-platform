package org.springboot.trendmartecommerceplatform.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springboot.trendmartecommerceplatform.address.Address;
import org.springboot.trendmartecommerceplatform.review.Review;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
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
    private boolean verified;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER; // Default role

    private boolean enabled = true; // Control activation

    // === Spring Security ===

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // For simplicity
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // You can add a field later to control this
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // You can change this later if needed
    }

    @Override
    public boolean isEnabled() {
        return enabled; // Use the actual field
    }



    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Review> review = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Address address;
}
