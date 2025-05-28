//package com.vena.ecom.service;
//
//import java.util.List;
//
//import com.vena.ecom.dto.request.LoginRequest;
//import com.vena.ecom.dto.request.UserRequest;
//import com.vena.ecom.dto.response.AddressResponse;
//import com.vena.ecom.dto.response.NewUserResponse;
//import com.vena.ecom.dto.response.UserResponse;
//import com.vena.ecom.model.User;
//import com.vena.ecom.model.Address;
//import com.vena.ecom.dto.request.AddAddressRequest;
//
//public interface UserService {
//    UserResponse getCurrentUser(String id);
//
//    UserResponse updateCurrentUser(User userDetails);
//
//    List<AddressResponse> getUserAddresses(String id);
//
//    AddressResponse addUserAddress(AddAddressRequest addAdressRequest, String id);
//
//    AddressResponse updateUserAddress(String addressId, Address addressDetails);
//
//    void deleteUserAddress(String addressId);
//
//    NewUserResponse registerUser(UserRequest request);
//
//    String login(LoginRequest request);
//
//    void deleteUserById(String userId);
//
//    NewUserResponse updateUserById(String userId, UserRequest request);
//}
package com.vena.ecom.service;

import java.util.List;

import com.vena.ecom.dto.request.LoginRequest;
import com.vena.ecom.dto.request.UserRequest;
import com.vena.ecom.dto.response.AddressResponse;
import com.vena.ecom.dto.response.NewUserResponse;
import com.vena.ecom.dto.response.UserResponse;
import com.vena.ecom.model.User;
import com.vena.ecom.model.Address;
import com.vena.ecom.dto.request.AddAddressRequest;

public interface UserService {
    UserResponse getCurrentUser(String id);

    UserResponse updateCurrentUser(User userDetails);

    List<AddressResponse> getUserAddresses(String id);

    AddressResponse addUserAddress(AddAddressRequest addAdressRequest, String id);

    AddressResponse updateUserAddress(String addressId, Address addressDetails);

    void deleteUserAddress(String addressId);

    NewUserResponse registerUser(UserRequest request);

    String login(LoginRequest request);

    void deleteUserById(String userId);

    NewUserResponse updateUserById(String userId, UserRequest request);
}
