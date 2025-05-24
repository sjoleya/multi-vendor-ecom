package com.vena.ecom.service.impl;

import java.util.List;
import java.util.Optional;

import com.vena.ecom.exception.ResourceNotFoundException;
import com.vena.ecom.model.User;
import com.vena.ecom.model.Address;
import com.vena.ecom.repo.AddressRepository;
import com.vena.ecom.repo.UserRepository;
import com.vena.ecom.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository userAddressRepository;

    @Override
    public User getCurrentUser() {
        logger.info("Fetching current user by email");
        return userRepository.findByEmail("john.doe@example.com")
                .orElseThrow(() -> {
                    logger.warn("User not found with email: john.doe@example.com");
                    return new ResourceNotFoundException("User not found!");
                });
    }

    @Override
    public User updateCurrentUser(User userDetails) {
        logger.info("Updating user with ID: {}", userDetails.getId());
        Optional<User> optionalUser = userRepository.findById(userDetails.getId());
        if (!optionalUser.isPresent()) {
            logger.warn("User not found with ID: {}", userDetails.getId());
            throw new ResourceNotFoundException("User not found with ID: " + userDetails.getId());
        }
        User user = optionalUser.get();
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setRole(userDetails.getRole());
        User updatedUser = userRepository.save(user);
        logger.debug("User updated successfully with ID: {}", updatedUser.getId());
        return updatedUser;
    }

    @Override
    public List<Address> getUserAddresses(String userId) {
        logger.info("Fetching addresses for user ID: {}", userId);
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            List<Address> addresses = userAddressRepository.findByUser_Id(userId);
            logger.debug("Total addresses found: {}", addresses.size());
            return addresses;
        } else {
            logger.warn("User not found with ID: {}", userId);
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
    }

    @Override
    public Address addUserAddress(String userId, Address address) {
        logger.info("Adding new address for user ID: {}", userId);
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            logger.warn("User not found with ID: {}", userId);
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
        User user = optionalUser.get();
        Address savedAddress = userAddressRepository.save(address);
        user.getAddressList().add(savedAddress);
        userRepository.save(user);
        logger.debug("Address added successfully with ID: {}", savedAddress.getId());
        return savedAddress;
    }

    @Override
    public Address updateUserAddress(String addressId, Address addressDetails) {
        logger.info("Updating address with ID: {}", addressId);
        Optional<Address> optionalAddress = userAddressRepository.findById(addressId);
        if (optionalAddress.isPresent()) {
            Address address = optionalAddress.get();
            address.setStreet(addressDetails.getStreet());
            address.setCity(addressDetails.getCity());
            address.setState(addressDetails.getState());
            address.setZipCode(addressDetails.getZipCode());
            address.setCountry(addressDetails.getCountry());
            Address updated = userAddressRepository.save(address);
            logger.debug("Address updated successfully with ID: {}", updated.getId());
            return updated;
        } else {
            logger.warn("Address not found with ID: {}", addressId);
            throw new ResourceNotFoundException("Address not found with ID: " + addressId);
        }
    }

    @Override
    public void deleteUserAddress(String addressId) {
        logger.info("Deleting address with ID: {}", addressId);
        if (!userAddressRepository.existsById(addressId)) {
            logger.warn("Address not found for deletion with ID: {}", addressId);
            throw new ResourceNotFoundException("Address not found with ID: " + addressId);
        }
        userAddressRepository.deleteById(addressId);
        logger.debug("Address deleted successfully with ID: {}", addressId);
    }
}
