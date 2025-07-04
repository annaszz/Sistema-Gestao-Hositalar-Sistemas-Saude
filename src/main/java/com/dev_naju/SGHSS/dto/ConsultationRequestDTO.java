package com.dev_naju.SGHSS.dto;

import com.dev_naju.SGHSS.entities.Consultation;
import com.dev_naju.SGHSS.enums.ProfessionalRole;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ConsultationRequestDTO(
        String typeConsultation,
        LocalDateTime date,
        Long patientId,
        ProfessionalRole professionalRole
){ }
