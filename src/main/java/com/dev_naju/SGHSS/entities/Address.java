package com.dev_naju.SGHSS.entities;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private String street;
    private int number;
    private String city;
    private String state;
    private String complement;

    public Address(){}

    public Address(String street, int number, String city, String state) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.state = state;
    }

    public Address(String street, int number, String city, String state, String complement) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.state = state;
        this.complement = complement;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", number=" + number +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", complement='" + complement + '\'' +
                '}';
    }
}
