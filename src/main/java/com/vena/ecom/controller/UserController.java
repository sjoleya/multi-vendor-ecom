package com.vena.ecom.controller;

import com.vena.ecom.dto.response.UserResponse;
import com.vena.ecom.dto.response.AddressResponse;
import com.vena.ecom.model.User;
import com.vena.ecom.model.Address;
import com.vena.ecom.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users/me")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<UserResponse> getCurrentUser() {
        logger.info("Received request to get current user");
        User user = userService.getCurrentUser();
        logger.debug("Current user fetched: {}", user.getEmail());
        return ResponseEntity.ok(toUserResponse(user));
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateCurrentUser(@RequestBody User userDetails) {
        logger.info("Request to update current user profile");
        if (userDetails == null || userDetails.getId() == null) {
            logger.warn("User update request received with null or incomplete data");
        }
        User updated = userService.updateCurrentUser(userDetails);
        logger.debug("User updated: {}", updated.getEmail());
        return ResponseEntity.ok(toUserResponse(updated));
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<AddressResponse>> getUserAddresses() {
        logger.info("Fetching address list of current user");
        User user = userService.getCurrentUser();
        List<Address> addresses = userService.getUserAddresses(user.getId());
        if (addresses.isEmpty()) {
            logger.warn("User with ID {} has no saved addresses", user.getId());
        } else {
            logger.debug("User has {} addresses", addresses.size());
        }
        List<AddressResponse> response = addresses.stream().map(this::toAddressResponse).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/addresses")
    public ResponseEntity<AddressResponse> addUserAddress(@RequestBody Address address) {
        logger.info("Request to add a new address for current user");
        if (address == null || address.getStreet() == null) {
            logger.warn("Address creation request received with missing street information");
        }
        User user = userService.getCurrentUser();
        Address saved = userService.addUserAddress(user.getId(), address);
        logger.debug("Address saved with ID: {}", saved.getId());
        return ResponseEntity.ok(toAddressResponse(saved));
    }

    @PutMapping("/addresses/{id}")
    public ResponseEntity<AddressResponse> updateUserAddress(@PathVariable String id,
                                                             @RequestBody Address addressDetails) {
        logger.info("Updating address with ID: {}", id);
        if (id == null || id.trim().isEmpty()) {
            logger.warn("Empty ID provided for address update");
        }
        Address updated = userService.updateUserAddress(id, addressDetails);
        logger.debug("Address updated for ID: {}", id);
        return ResponseEntity.ok(toAddressResponse(updated));
    }

    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<Void> deleteUserAddress(@PathVariable String id) {
        logger.info("Deleting address with ID: {}", id);
        if (id == null || id.trim().isEmpty()) {
            logger.warn("Delete request received with empty address ID");
        }
        userService.deleteUserAddress(id);
        logger.info("Successfully deleted address with ID: {}", id);
        return ResponseEntity.ok().build();
    }

    private UserResponse toUserResponse(User user) {
        UserResponse dto = new UserResponse();
        dto.id = user.getId();
        dto.name = user.getFirstName() + " " + user.getLastName();
        dto.email = user.getEmail();
        dto.role = user.getRole() != null ? user.getRole().name() : null;
        dto.addresses = user.getAddressList() != null
                ? user.getAddressList().stream().map(this::toAddressResponse).collect(Collectors.toList())
                : null;
        return dto;
    }

    private AddressResponse toAddressResponse(Address address) {
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
