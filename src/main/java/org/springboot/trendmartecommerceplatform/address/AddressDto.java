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
    private String street;
    private String city;
    @NotBlank
    private String state;
    @NotNull
    private String Country;
    @NotNull
    private String PostalCode;
    @NotNull
    private Boolean isDefault;
    private Long userId;

}