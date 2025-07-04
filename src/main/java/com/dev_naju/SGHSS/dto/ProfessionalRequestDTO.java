package com.dev_naju.SGHSS.dto;

import com.dev_naju.SGHSS.entities.ProfessionalHealth;
import com.dev_naju.SGHSS.enums.ProfessionalRole;

public record ProfessionalRequestDTO(
        String name,
        String crm,
        ProfessionalRole role)
{
    public ProfessionalRequestDTO(ProfessionalHealth professional){
        this(professional.getName(), professional.getCrm(), professional.getProfessionalRole());
    }
}
