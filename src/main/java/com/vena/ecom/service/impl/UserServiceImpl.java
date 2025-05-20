package com.vena.ecom.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.vena.ecom.exception.ResourceNotFoundException;
import com.vena.ecom.model.Address;
import com.vena.ecom.model.User;
import com.vena.ecom.repo.AddressRepository;
import com.vena.ecom.repo.UserRepository;
import com.vena.ecom.service.UserService;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public User getCurrentUser() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCurrentUser'");
    }

    @Override
    public User updateCurrentUser(User userDetails) {
        Optional<User> optionalUser = userRepository.findById(userDetails.getUserId());
        if (!optionalUser.isPresent()) {
            throw new ResourceNotFoundException("User not found with ID: " + userDetails.getUserId());
        }
        User user = optionalUser.get();
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setRole(userDetails.getRole());
        return userRepository.save(user);
    }

    @Override
    public List<Address> getUserAddresses(String userId) {
        return addressRepository.findByUserUserId(userId);
    }

    @Override
    public Address addUserAddress(String userId, Address address) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            address.setUser(optionalUser.get());
            return addressRepository.save(address);
        } else {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
    }

    @Override
    public Address updateUserAddress(String addressId, Address addressDetails) {
        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        if (optionalAddress.isPresent()) {
            Address address = optionalAddress.get();
            address.setStreet(addressDetails.getStreet());
            address.setCity(addressDetails.getCity());
            address.setState(addressDetails.getState());
            address.setZipCode(addressDetails.getZipCode());
            address.setCountry(addressDetails.getCountry());
            address.setAddressType(addressDetails.getAddressType());
            return addressRepository.save(address);
        } else {
            throw new ResourceNotFoundException("Address not found with ID: " + addressId);
        }
    }

    @Override
    public void deleteUserAddress(String addressId) {
        if (!addressRepository.existsById(addressId)) {
            throw new ResourceNotFoundException("Address not found with ID: " + addressId);
        }
        addressRepository.deleteById(addressId);
    }

}
