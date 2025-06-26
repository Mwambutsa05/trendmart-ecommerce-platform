package org.springboot.trendmartecommerceplatform.address;


import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
@SecurityRequirement(name = "auth")
public class AddressController {
    private final AddressService addressService;


    @PostMapping
    @Operation(summary = "add address")
    public ResponseEntity<Address> addAddress(@Valid @RequestBody AddressDto Dtoo) {
        Address address = addressService.createAddress(Dtoo);
        return new ResponseEntity<>(address, HttpStatus.CREATED);
    }
    @GetMapping("/user/{userId}")
        @Operation(summary = "get address by userId")
        public ResponseEntity<Address> getAddressByUserId(@Valid @PathVariable Long userId) {
        Address address = addressService.getByUserId(userId);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    @Operation(summary = "get address by id")
    public ResponseEntity<Address> getAddressById(@Valid @PathVariable Long id) {
        Address address = addressService.getById(id);
        return new ResponseEntity<>(address, HttpStatus.OK);

    }
    @PutMapping("/{id}")
    @Operation(summary = "update address")
    public ResponseEntity<Address> updateAddress(@Valid @PathVariable Long id , @RequestBody AddressDto Dtoo) {
        Address address = addressService.updateAddress(id, Dtoo);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "delete address")
    public ResponseEntity<Address> deleteAddress( @Valid @PathVariable Long id) {
        addressService.deleteAddress(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
