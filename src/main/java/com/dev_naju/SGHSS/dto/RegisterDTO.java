package com.dev_naju.SGHSS.dto;

import com.dev_naju.SGHSS.enums.ProfessionalRole;
import com.dev_naju.SGHSS.enums.UserRole;

import java.time.LocalDate;

public record RegisterDTO(
        String login,
        String password,
        String name,
        UserRole role,

        String gender,
        LocalDate dateBirth,
        AddressDTO address,

        String crm,
        ProfessionalRole professionalRole
        ) {
}
