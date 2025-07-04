package com.dev_naju.SGHSS.dto;

import com.dev_naju.SGHSS.entities.Patient;

import java.time.LocalDate;

public record PatientResponseAllDTO(
        Long id,
        String name,
        String email,
        String gender,
        LocalDate dateBirth,
        AddressDTO address
) {
    public PatientResponseAllDTO(Patient patient) {
        this(patient.getId(), patient.getName(), patient.getLogin(), patient.getGender(), patient.getDateBirth(), new AddressDTO(patient.getAddress()));
    }

}
