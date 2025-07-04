package com.dev_naju.SGHSS.repositories;

import com.dev_naju.SGHSS.entities.ProfessionalHealth;
import com.dev_naju.SGHSS.enums.ProfessionalRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessionalRepository extends JpaRepository<ProfessionalHealth, Long> {
    List<ProfessionalHealth> findByProfessionalRole(ProfessionalRole professionalRole);
    Optional<ProfessionalHealth> findByCrm(String crm);
    Optional<ProfessionalHealth> findByLogin(String login);
}
