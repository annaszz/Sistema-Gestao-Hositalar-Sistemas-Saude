package com.dev_naju.SGHSS.dto;

import com.dev_naju.SGHSS.entities.ProfessionalHealth;
import com.dev_naju.SGHSS.enums.ProfessionalRole;

public record ProfessionalResponseDTO(
        Long id,
        String name,
        ProfessionalRole professionalRole
) {
    public ProfessionalResponseDTO(ProfessionalHealth professional) {
        this(professional.getId(), professional.getName(), professional.getProfessionalRole());
    }
}
