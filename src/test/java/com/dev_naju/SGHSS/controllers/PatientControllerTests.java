package com.dev_naju.SGHSS.controllers;

import com.dev_naju.SGHSS.entities.Address;
import com.dev_naju.SGHSS.entities.Patient;
import com.dev_naju.SGHSS.repositories.PatientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PatientRepository patientRepository;

    @BeforeEach
    void setUp(){
        patientRepository.deleteAll();
    }

    Address patientAddress = new Address("rua 123", 36, "Sao Paulo", "SP");

    LocalDate localDate = LocalDate.of(2004, 9, 30);
    Date datePatient = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

    @Test
    @DisplayName("Register New Patient")
    void shouldRegisterNewPatientAndVerifyCountIncrease() throws Exception {
        long initialPatients = patientRepository.count();
        System.out.println("intital: " + initialPatients);

        Patient newPatient = new Patient("Anna", "anna@email", "Female", datePatient, patientAddress);

        ResultActions response = mockMvc.perform(post("/patients/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPatient))).andDo(print());

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());

        long finalPatientCount = patientRepository.count();
        assertThat(finalPatientCount).isEqualTo(initialPatients + 1);

        }

}
