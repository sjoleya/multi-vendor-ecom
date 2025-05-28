package com.vena.ecom.service.impl;

import java.util.List;
import java.util.Optional;

import com.vena.ecom.dto.request.LoginRequest;
import com.vena.ecom.dto.request.UserRequest;
import com.vena.ecom.dto.response.AddressResponse;
import com.vena.ecom.dto.response.NewUserResponse;
import com.vena.ecom.dto.response.UserResponse;
import com.vena.ecom.exception.ResourceNotFoundException;
import com.vena.ecom.model.User;
import com.vena.ecom.model.VendorProfile;
import com.vena.ecom.model.enums.AddressType;
import com.vena.ecom.model.Address;
import com.vena.ecom.dto.request.AddAddressRequest;
import com.vena.ecom.model.enums.ApprovalStatus;
import com.vena.ecom.model.enums.UserRole;
import com.vena.ecom.repo.AddressRepository;
import com.vena.ecom.repo.UserRepository;
import com.vena.ecom.repo.VendorProfileRepository;
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
    private AddressRepository addressRepository;

    @Autowired
    VendorProfileRepository vendorProfileRepository;

    @Override
    public UserResponse getCurrentUser(String id) {
        logger.info("Fetching current user by email");
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("User not found with email: john.doe@example.com");
                    return new ResourceNotFoundException("User not found!");
                });

        return new UserResponse(user);
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
        return new UserResponse(updatedUser);
    }

    @Override
    public List<AddressResponse> getUserAddresses(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        List<Address> addresses = addressRepository.findByUser(user);
        logger.info("Total addresses found: {}", addresses.size());
        return addresses.stream().map(AddressResponse::new).toList();
    }

    @Override
    public AddressResponse addUserAddress(AddAddressRequest addAdressRequest, String id) {
        // String userId = getCurrentUser().getUserId();
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            logger.warn("User not found with ID: {}", id);
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
        User user = optionalUser.get();
        // address.setUser(user);
        Address address = new Address();
        address.setStreet(addAdressRequest.getStreet());
        address.setCity(addAdressRequest.getCity());
        address.setState(addAdressRequest.getState());
        address.setZipCode(addAdressRequest.getZip());
        address.setCountry(addAdressRequest.getCountry());
        address.setUser(user);
        try {
            address.setAddressType(AddressType.valueOf(addAdressRequest.getType().toUpperCase()));
        } catch (IllegalArgumentException e) {
            // Handle the case where the provided type is not a valid AddressType
            throw new IllegalArgumentException("Invalid address type: " + addAdressRequest.getType());
        }
        Address savedAddress = addressRepository.save(address);
        user.getAddressList().add(savedAddress);
        userRepository.save(user);
        logger.info("Address added successfully with ID: {}", savedAddress.getId());
        return new AddressResponse(addressRepository.save(address));
    }

    @Override
    public AddressResponse updateUserAddress(String addressId, Address addressDetails) {
        logger.info("Updating address with ID: {}", addressId);
        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        if (optionalAddress.isPresent()) {
            Address address = optionalAddress.get();
            address.setStreet(addressDetails.getStreet());
            address.setCity(addressDetails.getCity());
            address.setState(addressDetails.getState());
            address.setZipCode(addressDetails.getZipCode());
            address.setCountry(addressDetails.getCountry());
            Address updated = addressRepository.save(address);
            logger.info("Address updated successfully with ID: {}", updated.getId());
            return new AddressResponse(addressRepository.save(address));
        } else {
            logger.warn("Address not found with ID: {}", addressId);
            throw new ResourceNotFoundException("Address not found with ID: " + addressId);
        }
    }

    @Override
    public void deleteUserAddress(String addressId) {
        logger.info("Deleting address with ID: {}", addressId);
        if (!addressRepository.existsById(addressId)) {
            logger.warn("Address not found for deletion with ID: {}", addressId);
            throw new ResourceNotFoundException("Address not found with ID: " + addressId);
        }
        addressRepository.deleteById(addressId);
        logger.info("Address deleted successfully with ID: {}", addressId);
    }

    @Override
    public NewUserResponse registerUser(UserRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(request.getPassword());
        user.setRole(request.getRole());
        user.setPhoneNumber(request.getPhoneNumber());
        User savedUser = userRepository.save(user);
        return new NewUserResponse(savedUser);
    }

    @Override
    public void deleteUserById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        userRepository.delete(user);
    }

    @Override
    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()->new ResourceNotFoundException("User not found with this email"));
        if(user.getPasswordHash().equals(request.getPassword())) {
            return "Login successful for " + user.getRole();
        }
        else {
            throw new IllegalArgumentException("Invalid Credentials");
        }
    }

    @Override
    public NewUserResponse updateUserById(String userId, UserRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(request.getPassword());
        user.setRole(request.getRole());
        user.setPhoneNumber(request.getPhoneNumber());

        User savedUser = userRepository.save(user);
        return new NewUserResponse(savedUser);
    }
}
