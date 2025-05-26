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
        logger.info("Get /users/me -  get current user");
        UserResponse userResponse = userService.getCurrentUser();
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateCurrentUser(@RequestBody User userDetails) {
        logger.info("Put /users/me   - Request to update current user profile");
        if (userDetails == null) {
            logger.warn("User update request received with null or incomplete data");
        }
        UserResponse updated = userService.updateCurrentUser(userDetails);
        logger.info("User updated: {}", updated.getEmail());
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<AddressResponse>> getUserAddresses() {
        logger.info("Get /user/me/addresses - Fetching address list( all user ) of current user");
        UserResponse user = userService.getCurrentUser();
        List<AddressResponse> addresses = userService.getUserAddresses(user.getId());
        if (addresses.isEmpty()) {
            logger.warn("User with ID {} has no saved addresses", user.getId());
        } else {
            logger.info("User has {} addresses", addresses.size());
        }

        return ResponseEntity.ok(addresses);
    }

    @PostMapping("/addresses")
    public ResponseEntity<AddressResponse> addUserAddress(@RequestBody Address address) {
        logger.info("Post /user/me/addresses - Request to add a new address for current user");
        if (address == null || address.getStreet() == null) {
            logger.warn("Address creation request received with missing street information");
        }
        UserResponse userResponse = userService.getCurrentUser();
        AddressResponse saved = userService.addUserAddress(userResponse.getId(), address);
        logger.info("Address saved with ID: {}", saved.getId());
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/addresses/{id}")
    public ResponseEntity<AddressResponse> updateUserAddress(@PathVariable String id,
                                                             @RequestBody Address addressDetails) {
        logger.info("Put /user/me/addresses/id - Updating address with ID: {}", id);
        if (id == null || id.trim().isEmpty()) {
            logger.warn("Empty ID provided for address update");
        }
        AddressResponse updated = userService.updateUserAddress(id, addressDetails);
        logger.info("Address updated for ID: {}", id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<Void> deleteUserAddress(@PathVariable String id) {
        logger.info("Delete /user/me/addresses/id - Deleting address with ID: {}", id);
        if (id == null || id.trim().isEmpty()) {
            logger.warn("Delete request received with empty address ID");
        }
        userService.deleteUserAddress(id);
        logger.info("Successfully deleted address with ID: {}", id);
        return ResponseEntity.ok().build();
    }

}
