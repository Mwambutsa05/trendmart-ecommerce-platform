package org.springboot.trendmartecommerceplatform.address;

import lombok.AllArgsConstructor;
import org.springboot.trendmartecommerceplatform.exceptionHandling.ResourceNotFound;
import org.springboot.trendmartecommerceplatform.user.User;
import org.springboot.trendmartecommerceplatform.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public Address createAddress(AddressDto Dtoo) {
        System.out.println("Service method started");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName(); 

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFound("User not found"));

        System.out.println("User retrival line reached");

        Address address = new Address();
        address.setUser(user);
        address.setStreet(Dtoo.getStreet());
        address.setCity(Dtoo.getCity());
        address.setState(Dtoo.getState());
        address.setPostalCode(Dtoo.getPostalCode());
        address.setCountry(Dtoo.getCountry());
        address.setIsDefault(Dtoo.getIsDefault());

        System.out.println("Cant add address");

        return addressRepository.save(address);
    }

    public Address getByUserId(Long userId) {
        return addressRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFound("User not found"));
    }

    public Address getById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Address not found"));
    }

    public Address updateAddress(Long id, AddressDto Dtoo) {
        Address updateAddress = addressRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Address not found"));
        updateAddress.setStreet(Dtoo.getStreet());
        updateAddress.setCity(Dtoo.getCity());
        updateAddress.setState(Dtoo.getState());
        updateAddress.setPostalCode(Dtoo.getPostalCode());
        updateAddress.setCountry(Dtoo.getCountry());
        updateAddress.setIsDefault(Dtoo.getIsDefault());
        return addressRepository.save(updateAddress);
    }

    public void deleteAddress(Long id) {
        Address deleteAddress = addressRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Address not found"));
        addressRepository.delete(deleteAddress);
    }
}