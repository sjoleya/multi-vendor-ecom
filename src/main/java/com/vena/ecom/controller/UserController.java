package com.vena.ecom.controller;

import com.vena.ecom.dto.response.UserResponse;
import com.vena.ecom.dto.response.AddressResponse;
import com.vena.ecom.model.User;
import com.vena.ecom.model.Address;
import com.vena.ecom.dto.request.AddAdressRequest;
import com.vena.ecom.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        logger.info("GET /users/me - Get current user");
        UserResponse userResponse = userService.getCurrentUser();
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateCurrentUser(@RequestBody User userDetails) {
        logger.info("PUT /users/me - Request to update current user profile");
        UserResponse updated = userService.updateCurrentUser(userDetails);
        logger.info("User updated: {}", updated.getEmail());
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/me/addresses")
    public ResponseEntity<List<AddressResponse>> getUserAddresses() {
        logger.info("GET /users/me/addresses - Fetching address list of current user");
        List<AddressResponse> addresses = userService.getUserAddresses();
        return ResponseEntity.ok(addresses);
    }

    @PostMapping("/addresses")
    public ResponseEntity<AddressResponse> addUserAddress(@RequestBody AddAdressRequest address) {
        logger.info("Post /users/me/addresses - Request to add a new address for current user");
        AddressResponse saved = userService.addUserAddress(address);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/me/addresses/{id}")
    public ResponseEntity<AddressResponse> updateUserAddress(@PathVariable String id,
            @RequestBody Address addressDetails) {
        logger.info("Put /users/me/addresses/id - Updating address with ID: {}", id);
        AddressResponse updated = userService.updateUserAddress(id, addressDetails);
        logger.info("Address updated for ID: {}", id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/me/addresses/{id}")
    public ResponseEntity<Void> deleteUserAddress(@PathVariable String id) {
        logger.info("Delete /users/me/addresses/id - Deleting address with ID: {}", id);
        if (id == null || id.trim().isEmpty()) {
            logger.warn("Delete request received with empty address ID");
        }
        userService.deleteUserAddress(id);
        logger.info("Successfully deleted address with ID: {}", id);
        return ResponseEntity.ok().build();
    }
}
