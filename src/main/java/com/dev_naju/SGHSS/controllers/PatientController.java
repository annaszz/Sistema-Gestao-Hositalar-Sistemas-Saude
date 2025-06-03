package com.dev_naju.SGHSS.controllers;

import com.dev_naju.SGHSS.dto.PatientDTO;
import com.dev_naju.SGHSS.entities.Patient;
import com.dev_naju.SGHSS.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/patients")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @GetMapping
    public List<PatientDTO> findAll(){
        List<Patient> patients = patientService.findAll();
        return patients.stream().map(PatientDTO::new).collect(Collectors.toList());
    }

}
