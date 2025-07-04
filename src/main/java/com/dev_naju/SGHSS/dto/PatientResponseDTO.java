package com.dev_naju.SGHSS.dto;

import com.dev_naju.SGHSS.entities.Patient;

public record PatientResponseDTO(
        Long id,
        String name,
        String email
) {
    public PatientResponseDTO(Patient patient) {
        this(patient.getId(), patient.getName(), patient.getLogin());
    }
}
