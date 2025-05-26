package com.vena.ecom.dto.response;

public class AddressResponse {
    public String addressId;
    public String street;
    public String city;
    public String state;
    public String zip;
    public String country;
    public String type;

    public AddressResponse() {
    }

    public AddressResponse(String id, String street, String city, String state, String zip, String country, String type) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
