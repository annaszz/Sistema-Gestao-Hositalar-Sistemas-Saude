package com.dev_naju.SGHSS.repositories;

import com.dev_naju.SGHSS.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByLogin(String login);
}
