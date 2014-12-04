package com.eventapp.prototype.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Embeddable
public class Address {
    //@Id
    //@GeneratedValue
    //private long id;

    private String street1;

    private String street2;

    private String city;

    private String state;

    private String postalCode;

    private String country;

    protected Address() {}

    public Address(String street1, String street2, String city, String state, String postalCode, String country) {
        this.street1 = street1;
        this.street2 = street2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
    }

    /*public long getId() {
        return id;
    }*/

    public String getStreet1() {
        return street1;
    }

    public String getStreet2() {
        return street2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }
}
