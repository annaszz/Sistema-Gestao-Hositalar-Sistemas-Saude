package com.dev_naju.SGHSS.dto;

import com.dev_naju.SGHSS.entities.Consultation;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ConsultationResponseDTO(
        Long id,
        String typeConsultation,
        LocalDateTime date,
        PatientResponseDTO patient,
        ProfessionalResponseDTO professionalSummaryDTO
) {
    public ConsultationResponseDTO(Consultation consultation) {
        this(
                consultation.getId(),
                consultation.getTypeConsultation(),
                consultation.getDate(),
//                consultation.isOnline(), // Mapeie
//                consultation.getMeetingLink(), // Mapeie
                consultation.getPatient() != null ? new PatientResponseDTO(consultation.getPatient()) : null,
                consultation.getProfessional() != null ? new ProfessionalResponseDTO(consultation.getProfessional()) : null
        );
    }

}
