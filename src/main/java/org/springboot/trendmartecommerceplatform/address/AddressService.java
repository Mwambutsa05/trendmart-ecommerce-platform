package org.springboot.trendmartecommerceplatform.address;

import lombok.AllArgsConstructor;
import org.springboot.trendmartecommerceplatform.user.User;
import org.springboot.trendmartecommerceplatform.user.UserRepository;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public Address createAddress(AddressDto Dtoo) {
        User user = userRepository.findById(Dtoo.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Address address = new Address();
        address.setUser(user);
        address.setStreet(Dtoo.getStreet());
        address.setCity(Dtoo.getCity());
        address.setState(Dtoo.getState());
        address.setPostalCode(Dtoo.getPostalCode());
        address.setCountry(Dtoo.getCountry());
        address.setIsDefault(Dtoo.getIsDefault());
         return addressRepository.save(address);
    }
    public Address getByUserId(Long userId) {
        return addressRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("User not found"));

    }
    public Address getById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new RuntimeException("Address not found"));
    }
    public Address updateAddress(Long id,AddressDto Dtoo) {
        Address updateAddress = addressRepository.findById(id).orElseThrow(() -> new RuntimeException("Address not found"));
        updateAddress.setStreet(Dtoo.getStreet());
        updateAddress.setCity(Dtoo.getCity());
        updateAddress.setState(Dtoo.getState());
        updateAddress.setPostalCode(Dtoo.getPostalCode());
        updateAddress.setCountry(Dtoo.getCountry());
        updateAddress.setIsDefault(Dtoo.getIsDefault());
        return addressRepository.save(updateAddress);


    }
    public void  deleteAddress(Long id) {
        Address deleteAddress = addressRepository.findById(id).orElseThrow(() -> new RuntimeException("Address not found"));
        addressRepository.delete(deleteAddress);
    }

}
