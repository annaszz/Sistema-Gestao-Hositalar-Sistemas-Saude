package com.dev_naju.SGHSS.dto;

import com.dev_naju.SGHSS.entities.Address;

public class AddressDTO {
    private String street;
    private int number;
    private String city;
    private String state;
    private String complement;

    public AddressDTO(){}

    public AddressDTO(Address address){
        street = address.getStreet();
        number = address.getNumber();
        city = address.getCity();
        state = address.getState();
        complement = address.getComplement();
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
}
