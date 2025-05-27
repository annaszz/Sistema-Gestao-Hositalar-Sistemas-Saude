package com.dev_naju.SGHSS.dto;

import com.dev_naju.SGHSS.entities.Address;
import com.dev_naju.SGHSS.entities.Bed;
import com.dev_naju.SGHSS.entities.HealthcareUnit;

import java.util.Set;
import java.util.stream.Collectors;

public class HealthcareUnitDTO {
    private final Long id;
    private final String name;
    private final String unitType;
    private final AddressDTO address;

    public HealthcareUnitDTO(HealthcareUnit unit){
        id = unit.getId();
        name = unit.getName();
        unitType = unit.getUnitType();
        address = new AddressDTO(unit.getAddress());
    }

    public Long getId() {return id;}
    public String getName() {return name;}
    public String getUnitType() {return unitType;}
    public AddressDTO getAddress() {return address;}
}
