package com.dev_naju.SGHSS.services;

import com.dev_naju.SGHSS.entities.Patient;
import com.dev_naju.SGHSS.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> findAll(){
        return patientRepository.findAll();
    }

}
