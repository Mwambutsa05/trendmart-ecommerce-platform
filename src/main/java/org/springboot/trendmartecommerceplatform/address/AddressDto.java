package org.springboot.trendmartecommerceplatform.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * DTO for {@link org.springboot.trendmartecommerceplatform.address.Address}
 */
@AllArgsConstructor
@Data
@Getter
@ToString

public class AddressDto {
    @NotBlank
    private final String street;
    private final String city;
    @NotBlank
    private final String state;
    @NotNull
    private final String Country;
    @Size
    @NotNull
    private final String PostalCode;
    @NotNull
    private final Boolean isDefault;
    private Long userId;

}