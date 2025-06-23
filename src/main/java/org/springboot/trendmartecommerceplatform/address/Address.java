package org.springboot.trendmartecommerceplatform.address;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springboot.trendmartecommerceplatform.user.User;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address")
@Entity
public class Address  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private String city;
    private String state;
    private String Country;
    private String PostalCode;
    private Boolean isDefault;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference("users-address")
    private User user;

}