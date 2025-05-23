package com.vena.ecom.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vena.ecom.exception.ResourceNotFoundException;
import com.vena.ecom.model.User;
import com.vena.ecom.model.Address;
import com.vena.ecom.repo.AddressRepository;
import com.vena.ecom.repo.UserRepository;
import com.vena.ecom.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository userAddressRepository;

    @Override
    public User getCurrentUser() {
        return userRepository.findByEmail("john.doe@example.com")
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public User updateCurrentUser(User userDetails) {
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
        return userRepository.save(user);
    }

    @Override
    public List<Address> getUserAddresses(String userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            return userAddressRepository.findByUser_Id(userId);
        } else {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
    }

    @Override
    public Address addUserAddress(String userId, Address address) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
        User user = optionalUser.get();
//        address.setUser(user);
        Address savedAddress = userAddressRepository.save(address);
        user.getAddressList().add(savedAddress);
        userRepository.save(user);
        return savedAddress;
    }

    @Override
    public Address updateUserAddress(String addressId, Address addressDetails) {
        Optional<Address> optionalAddress = userAddressRepository.findById(addressId);
        if (optionalAddress.isPresent()) {
            Address address = optionalAddress.get();
            address.setStreet(addressDetails.getStreet());
            address.setCity(addressDetails.getCity());
            address.setState(addressDetails.getState());
            address.setZipCode(addressDetails.getZipCode());
            address.setCountry(addressDetails.getCountry());
            return userAddressRepository.save(address);
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
