package com.dev_naju.SGHSS.repositories;

import com.dev_naju.SGHSS.entities.HealthcareUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthcareUnitRepository extends JpaRepository<HealthcareUnit, Long> {
}
