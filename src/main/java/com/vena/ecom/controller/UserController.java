package com.vena.ecom.controller;

import com.vena.ecom.dto.response.UserResponse;
import com.vena.ecom.dto.response.AddressResponse;
import com.vena.ecom.model.User;
import com.vena.ecom.model.Address;
import com.vena.ecom.dto.request.AddAdressRequest;
import com.vena.ecom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateCurrentUser(@RequestBody User userDetails) {
        return ResponseEntity.ok(userService.updateCurrentUser(userDetails));
    }

    @GetMapping("/me/addresses")
    public ResponseEntity<List<AddressResponse>> getUserAddresses() {
        return ResponseEntity.ok(userService.getUserAddresses());
    }

    @PostMapping("/me/addresses")
    public ResponseEntity<AddressResponse> addUserAddress(@RequestBody AddAdressRequest addAdressRequest) {
        return ResponseEntity.ok(userService.addUserAddress(addAdressRequest));
    }

    @PutMapping("/me/addresses/{id}")
    public ResponseEntity<AddressResponse> updateUserAddress(@PathVariable String id,
            @RequestBody Address addressDetails) {
        return ResponseEntity.ok(userService.updateUserAddress(id, addressDetails));
    }

    @DeleteMapping("/me/addresses/{id}")
    public ResponseEntity<Void> deleteUserAddress(@PathVariable String id) {
        userService.deleteUserAddress(id);
        return ResponseEntity.ok().build();
    }
}
