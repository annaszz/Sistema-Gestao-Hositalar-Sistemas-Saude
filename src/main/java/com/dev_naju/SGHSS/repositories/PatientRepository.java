package com.dev_naju.SGHSS.repositories;

import com.dev_naju.SGHSS.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
