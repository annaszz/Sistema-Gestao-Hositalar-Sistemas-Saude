package com.dev_naju.SGHSS.repositories;

import com.dev_naju.SGHSS.entities.Consultation;
import com.dev_naju.SGHSS.entities.Patient;
import com.dev_naju.SGHSS.entities.ProfessionalHealth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    List<Consultation> findByPatient(Patient patient);
    List<Consultation> findByProfessional(ProfessionalHealth professionalHealth);
}
