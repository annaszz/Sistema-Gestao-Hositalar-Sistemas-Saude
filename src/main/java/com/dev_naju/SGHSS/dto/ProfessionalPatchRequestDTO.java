package com.dev_naju.SGHSS.dto;

import com.dev_naju.SGHSS.enums.ProfessionalRole;

import java.util.Optional;

public record ProfessionalPatchRequestDTO(
        Optional<String> name,
        Optional<String> email, // O e-mail será o login
        Optional<ProfessionalRole> specialty,
        Optional<String> crm

) {
}
