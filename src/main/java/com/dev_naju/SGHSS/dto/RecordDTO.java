package com.dev_naju.SGHSS.dto;

import com.dev_naju.SGHSS.entities.RecordPatient;

public record RecordDTO(
        Long id,
        Long patientId) {

    public RecordDTO(RecordPatient record) {
        this(
            record.getId(),
            record.getPatient().getId()
        );
    }
}
