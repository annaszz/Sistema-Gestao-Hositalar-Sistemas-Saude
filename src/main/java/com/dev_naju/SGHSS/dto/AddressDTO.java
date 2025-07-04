package com.dev_naju.SGHSS.dto;

import com.dev_naju.SGHSS.entities.Address;

public record AddressDTO(
        String street,
        int number,
        String city,
        String state,
        String complement) {

    public Address toEntity(){
        Address address = new Address();
        address.setStreet(this.street());
        address.setNumber(this.number());
        address.setCity(this.city());
        address.setState(this.state());
        address.setComplement(this.complement);
        return address;
    }

    public AddressDTO(Address address) {
        this(
                address.getStreet(),
                address.getNumber(),
                address.getCity(),
                address.getState(),
                address.getComplement()
        );
    }

    public AddressDTO(String street, int number, String city, String state) {
        this(street, number, city, state, null);
    }
}
