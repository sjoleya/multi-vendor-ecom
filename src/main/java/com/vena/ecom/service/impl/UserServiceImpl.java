package com.vena.ecom.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vena.ecom.dto.response.AddressResponse;
import com.vena.ecom.dto.response.UserResponse;
import com.vena.ecom.exception.ResourceNotFoundException;
import com.vena.ecom.model.User;
import com.vena.ecom.model.Address;
import com.vena.ecom.dto.request.AddAdressRequest;
import com.vena.ecom.repo.AddressRepository;
import com.vena.ecom.repo.UserRepository;
import com.vena.ecom.service.UserService;
import com.vena.ecom.model.enums.AddressType;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository userAddressRepository;

    @Override
    public UserResponse getCurrentUser() {
        User user = userRepository.findByEmail("john.doe@example.com")
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        return new UserResponse(user);
    }

    @Override
    public UserResponse updateCurrentUser(User userDetails) {
        Optional<User> optionalUser = userRepository.findById(userDetails.getId());
        if (!optionalUser.isPresent()) {
            throw new ResourceNotFoundException("User not found with ID: " + userDetails.getId());
        }
        User user = optionalUser.get();
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setRole(userDetails.getRole());
        User savedUser = userRepository.save(user);
        return new UserResponse(savedUser);
    }

    @Override
    public List<AddressResponse> getUserAddresses() {
        String userId = getCurrentUser().getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("User not found with ID: " + userId));
        return user.getAddressList().stream().map(AddressResponse::new).toList();
    }

    @Override
    public AddressResponse addUserAddress(AddAdressRequest addAdressRequest) {
        String userId = getCurrentUser().getUserId();
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
        User user = optionalUser.get();

        Address address = new Address();
        address.setStreet(addAdressRequest.getStreet());
        address.setCity(addAdressRequest.getCity());
        address.setState(addAdressRequest.getState());
        address.setZipCode(addAdressRequest.getZip());
        address.setCountry(addAdressRequest.getCountry());
        try {
            address.setAddressType(AddressType.valueOf(addAdressRequest.getType()));
        } catch (IllegalArgumentException e) {
            // Handle the case where the provided type is not a valid AddressType
            throw new IllegalArgumentException("Invalid address type: " + addAdressRequest.getType());
        }

        Address savedAddress = userAddressRepository.save(address);
        user.getAddressList().add(savedAddress);
        userRepository.save(user);
        return new AddressResponse(savedAddress);
    }

    @Override
    public AddressResponse updateUserAddress(String addressId, Address addressDetails) {
        Optional<Address> optionalAddress = userAddressRepository.findById(addressId);
        if (optionalAddress.isPresent()) {
            Address address = optionalAddress.get();
            address.setStreet(addressDetails.getStreet());
            address.setCity(addressDetails.getCity());
            address.setState(addressDetails.getState());
            address.setZipCode(addressDetails.getZipCode());
            address.setCountry(addressDetails.getCountry());
            return new AddressResponse(userAddressRepository.save(address));
        } else {
            throw new ResourceNotFoundException("Address not found with ID: " + addressId);
        }
    }

    @Override
    public void deleteUserAddress(String addressId) {
        if (!userAddressRepository.existsById(addressId)) {
            throw new ResourceNotFoundException("Address not found with ID: " + addressId);
        }
        userAddressRepository.deleteById(addressId);
    }

}
