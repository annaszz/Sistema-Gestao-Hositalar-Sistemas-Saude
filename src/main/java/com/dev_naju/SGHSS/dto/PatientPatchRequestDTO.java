package com.dev_naju.SGHSS.dto;

import java.time.LocalDate;
import java.util.Optional;

public record PatientPatchRequestDTO(
        Optional<String> name,
        Optional<String> email,
        Optional<String > gender,
        Optional<LocalDate> dateBirth,
        Optional<AddressDTO> address
) {
}
