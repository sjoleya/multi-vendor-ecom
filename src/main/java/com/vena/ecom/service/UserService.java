package com.vena.ecom.service;

import java.util.List;

import com.vena.ecom.model.Address;
import com.vena.ecom.model.User;

public interface UserService {
    User getCurrentUser();

    User updateCurrentUser(User userDetails);

    List<Address> getUserAddresses(String userId);

    Address addUserAddress(String userId, Address address);

    Address updateUserAddress(String addressId, Address addressDetails);

    void deleteUserAddress(String addressId);
}
