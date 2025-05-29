//package com.vena.ecom.dto.response;
//
//import com.vena.ecom.model.User;
//import com.vena.ecom.model.enums.UserRole;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static org.hibernate.cfg.JdbcSettings.USER;
//
//public class UserResponse {
//    private String userId;
//    private String firstname;
//    private String lastname;
//    private String email;
//    private UserRole role;
//    private List<AddressResponse> addresses;
//
//
////    public UserResponse(String userId, String firstname, String lastname, String email, UserRole role, List<AddressResponse> addresses) {
////        this.userId = userId;
////        this.firstname = firstname;
////        this.lastname = lastname;
////        this.email = email;
////        this.role = role;
////        this.addresses = addresses;
////    }
//
//    public UserResponse(User user) {
//        this.userId= user.getId();
//        this.firstname=user.getFirstName();
//        this.lastname=user.getLastName();
//        this.email=user.getEmail();
//        this.role = UserRole.valueOf(user.getRole().toString());
//        this.addresses=user.getAddressList().stream().map(AddressResponse::new).toList();
//    }
//
//    public String getUserId() {
//        return userId;
//    }
//}

package com.vena.ecom.dto.response;

import com.vena.ecom.model.User;
import com.vena.ecom.model.enums.UserRole;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserResponse {
    private String userId;
    private String firstname;
    private String lastname;
    private String email;
    private UserRole role;
    private List<AddressResponse> addresses;

    public UserResponse(User user) {
        this.userId = user.getId();
        this.firstname = user.getFirstName();
        this.lastname = user.getLastName();
        this.email = user.getEmail();
        this.role = user.getRole();

        // ✅ Fix: Handle null addressList safely
        if (user.getAddressList() != null) {
            this.addresses = user.getAddressList()
                    .stream()
                    .map(AddressResponse::new)
                    .collect(Collectors.toList());
        } else {
            this.addresses = Collections.emptyList();
        }
    }

    // ✅ Add all getters
    public String getUserId() {
        return userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public UserRole getRole() {
        return role;
    }

    public List<AddressResponse> getAddresses() {
        return addresses;
    }
}
