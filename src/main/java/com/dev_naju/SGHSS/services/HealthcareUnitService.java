package com.dev_naju.SGHSS.services;

import com.dev_naju.SGHSS.entities.HealthcareUnit;
import com.dev_naju.SGHSS.repositories.HealthcareUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthcareUnitService {
    @Autowired
    private HealthcareUnitRepository healthcareUnitRepository;

    public List<HealthcareUnit> findAll(){
        return healthcareUnitRepository.findAll();
    }

}
