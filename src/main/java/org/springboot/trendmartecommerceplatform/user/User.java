package org.springboot.trendmartecommerceplatform.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springboot.trendmartecommerceplatform.address.Address;
import org.springboot.trendmartecommerceplatform.review.Review;
import org.springboot.trendmartecommerceplatform.wishlist.WishlistItem;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String fullName;
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private String confirmPassword;
    private Date dateOfBirth;
    private boolean verified;

    


    private boolean enabled = false;


    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;


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
        return true;
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
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<WishlistItem> wishlistItems = new ArrayList<>();

}
