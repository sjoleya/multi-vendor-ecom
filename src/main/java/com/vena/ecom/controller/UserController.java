package com.vena.ecom.controller;

import com.vena.ecom.dto.request.LoginRequest;
import com.vena.ecom.dto.request.UserRequest;
import com.vena.ecom.dto.response.NewUserResponse;
import com.vena.ecom.dto.response.UserResponse;
import com.vena.ecom.dto.response.AddressResponse;
import com.vena.ecom.model.User;
import com.vena.ecom.model.Address;
import com.vena.ecom.dto.request.AddAddressRequest;
import com.vena.ecom.service.UserService;

import jakarta.validation.Valid;

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

    @PostMapping("/register")
    public ResponseEntity<NewUserResponse> registerUser(@Valid @RequestBody UserRequest request) {
        NewUserResponse saved = userService.registerUser(request);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable String userId, @Valid @RequestBody UserRequest request) {
        userService.updateUserById(userId, request);
        return ResponseEntity.ok("User updated successfully");
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        String result = userService.login(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logout successful");
    }

    @GetMapping("/me/{id}")
    public ResponseEntity<UserResponse> getUserByID(@PathVariable String id) {
        logger.info("GET /users/me - Get current user");
        UserResponse userResponse = userService.getCurrentUser(id);
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateCurrentUser(@Valid @RequestBody User userDetails) {
        logger.info("PUT /users/me - Request to update current user profile");
        UserResponse updated = userService.updateCurrentUser(userDetails);
        logger.info("User updated: {}", updated.getEmail());
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/me/addresses/{id}")
    public ResponseEntity<List<AddressResponse>> getUserAddresses(@PathVariable String id) {
        logger.info("GET /users/me/addresses - Fetching address list of current user");
        List<AddressResponse> addresses = userService.getUserAddresses(id);
        System.out.println(addresses);
        return ResponseEntity.ok(addresses);
    }

    @PostMapping("/me/addresses/{id}")
    public ResponseEntity<AddressResponse> addUserAddress(@Valid @RequestBody AddAddressRequest address,
            @PathVariable String id) {
        logger.info("Post /users/me/addresses - Request to add a new address for current user");
        AddressResponse saved = userService.addUserAddress(address, id);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/me/addresses/{id}")
    public ResponseEntity<AddressResponse> updateUserAddress(@PathVariable String id,
            @Valid @RequestBody Address addressDetails) {
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
