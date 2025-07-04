package com.dev_naju.SGHSS.services;

import com.dev_naju.SGHSS.dto.AddressDTO;
import com.dev_naju.SGHSS.entities.Patient;
import com.dev_naju.SGHSS.repositories.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTests {
    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    private PatientRequestDTO newPatient;
    private Patient savedPatient;

    AddressDTO patientAddress = new AddressDTO("rua 123", 36, "Sao Paulo", "SP");

    LocalDate localDate = LocalDate.of(2004, 9, 30);

    Date datePatient = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

    @BeforeEach
    void setUp(){
        newPatient = new PatientRequestDTO("Anna", "anna@email.com", "Female", datePatient, patientAddress);

        savedPatient = new Patient("Anna", "anna@email.com", "Female", datePatient, patientAddress.toEntity());
        savedPatient.setId(1L);
    }

    @Test
    @DisplayName("Save Success Patient")
    void ShouldSavePatientSuccessfully(){
        when(patientRepository.save(any(Patient.class))).thenReturn(savedPatient);

        Patient resultPatient = patientService.registerPatient(newPatient);

        assertThat(resultPatient).isNotNull();
        assertThat(resultPatient.getId()).isNotNull();
        assertThat(resultPatient.getName()).isEqualTo("Anna");

//        verify(patientRepository, times(1)).save()
    }


}
