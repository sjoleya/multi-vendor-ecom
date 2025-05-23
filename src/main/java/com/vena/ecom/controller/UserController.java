package com.vena.ecom.controller;

import com.vena.ecom.dto.response.UserResponse;
import com.vena.ecom.dto.response.AddressResponse;
import com.vena.ecom.model.User;
import com.vena.ecom.model.Address;
import com.vena.ecom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users/me")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<UserResponse> getCurrentUser() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(toUserResponse(user));
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateCurrentUser(@RequestBody User userDetails) {
        User updated = userService.updateCurrentUser(userDetails);
        return ResponseEntity.ok(toUserResponse(updated));
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<AddressResponse>> getUserAddresses() {
        User user = userService.getCurrentUser();
        List<Address> addresses = userService.getUserAddresses(user.getId());
        List<AddressResponse> response = addresses.stream().map(this::toAddressResponse).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/addresses")
    public ResponseEntity<AddressResponse> addUserAddress(@RequestBody Address address) {
        User user = userService.getCurrentUser();
        Address saved = userService.addUserAddress(user.getId(), address);
        return ResponseEntity.ok(toAddressResponse(saved));
    }

    @PutMapping("/addresses/{id}")
    public ResponseEntity<AddressResponse> updateUserAddress(@PathVariable String id,
            @RequestBody Address addressDetails) {
        Address updated = userService.updateUserAddress(id, addressDetails);
        return ResponseEntity.ok(toAddressResponse(updated));
    }

    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<Void> deleteUserAddress(@PathVariable String id) {
        userService.deleteUserAddress(id);
        return ResponseEntity.ok().build();
    }

    private UserResponse toUserResponse(User user) {
        UserResponse dto = new UserResponse();
        dto.userId = user.getId();
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
        dto.addressId = address.getId();
        dto.street = address.getStreet();
        dto.city = address.getCity();
        dto.state = address.getState();
        dto.zip = address.getZipCode();
        dto.country = address.getCountry();
        dto.type = address.getAddressType() != null ? address.getAddressType().name() : null;
        return dto;
    }
}
