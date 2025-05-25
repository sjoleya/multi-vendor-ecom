package com.vena.ecom.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.vena.ecom.dto.response.AddressResponse;
import com.vena.ecom.dto.response.UserResponse;
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
    public UserResponse getCurrentUser() {
        logger.info("Fetching current user by email");
        User user = userRepository.findByEmail("john.doe@example.com")
                .orElseThrow(() -> {
                    logger.warn("User not found with email: john.doe@example.com");
                    return new ResourceNotFoundException("User not found!");
                });

        return userResponseDto(user);
    }

    @Override
    public UserResponse updateCurrentUser(User userDetails) {
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
        logger.info("User updated successfully with ID: {}", updatedUser.getId());
        return userResponseDto(updatedUser);
    }

    @Override
    public List<AddressResponse> getUserAddresses(String userId) {
        logger.info("Fetching addresses for user ID: {}", userId);
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            List<Address> addresses = userAddressRepository.findByUser_Id(userId);
            logger.info("Total addresses found: {}", addresses.size());
            List<AddressResponse> response = addresses.stream().map(this::addressResponseDto).collect(Collectors.toList());
            return response;
        } else {
            logger.warn("User not found with ID: {}", userId);
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
    }

    @Override
    public AddressResponse addUserAddress(String userId, Address address) {
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
        logger.info("Address added successfully with ID: {}", savedAddress.getId());
        return addressResponseDto(savedAddress);
    }

    @Override
    public AddressResponse updateUserAddress(String addressId, Address addressDetails) {
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
            logger.info("Address updated successfully with ID: {}", updated.getId());
            return addressResponseDto(updated);
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
        logger.info("Address deleted successfully with ID: {}", addressId);
    }

    private UserResponse userResponseDto(User user) {
        UserResponse dto = new UserResponse();
        dto.id = user.getId();
        dto.name = user.getFirstName() + " " + user.getLastName();
        dto.email = user.getEmail();
        dto.role = user.getRole() != null ? user.getRole().name() : null;
        dto.addresses = user.getAddressList() != null
                ? user.getAddressList().stream().map(this::addressResponseDto).collect(Collectors.toList())
                : null;
        return dto;
    }

    private AddressResponse addressResponseDto(Address address) {
        AddressResponse dto = new AddressResponse();
        dto.id = address.getId();
        dto.street = address.getStreet();
        dto.city = address.getCity();
        dto.state = address.getState();
        dto.zip = address.getZipCode();
        dto.country = address.getCountry();
        dto.type = address.getAddressType() != null ? address.getAddressType().name() : null;
        return dto;
    }
}
