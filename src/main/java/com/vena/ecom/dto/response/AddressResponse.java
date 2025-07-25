package com.vena.ecom.dto.response;

import com.vena.ecom.model.Address;

public class AddressResponse {
    private String addressId;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String type;

    public AddressResponse() {
    }

    public AddressResponse(Address address) {
        this.addressId = address.getId();
        this.street = address.getStreet();
        this.city = address.getCity();
        this.state = address.getState();
        this.zip = address.getZipCode();
        this.country = address.getCountry();
        this.type = address.getAddressType() != null ? address.getAddressType().toString() : null;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
