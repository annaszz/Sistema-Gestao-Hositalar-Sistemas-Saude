package com.dev_naju.SGHSS.dto;

import com.dev_naju.SGHSS.entities.HealthcareUnit;

public record HealthcareUnitDTO(
        Long id,
        String name,
        String unitType,
        AddressDTO addressDTO) {

    public HealthcareUnitDTO(HealthcareUnit unit) {
        this(
            unit.getId(),
            unit.getName(),
            unit.getUnitType(),
            new AddressDTO(unit.getAddress())
            );
    }
}
