package com.vena.ecom.service.impl;

import java.util.List;

import com.vena.ecom.model.Address;
import com.vena.ecom.model.User;
import com.vena.ecom.service.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public User getCurrentUser() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCurrentUser'");
    }

    @Override
    public User updateCurrentUser(User userDetails) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateCurrentUser'");
    }

    @Override
    public List<Address> getUserAddresses(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserAddresses'");
    }

    @Override
    public Address addUserAddress(String userId, Address address) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addUserAddress'");
    }

    @Override
    public Address updateUserAddress(String addressId, Address addressDetails) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUserAddress'");
    }

    @Override
    public void deleteUserAddress(String addressId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteUserAddress'");
    }

}
