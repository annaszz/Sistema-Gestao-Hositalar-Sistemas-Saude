package com.dev_naju.SGHSS.controllers;

import com.dev_naju.SGHSS.dto.HealthcareUnitDTO;
import com.dev_naju.SGHSS.entities.HealthcareUnit;
import com.dev_naju.SGHSS.services.HealthcareUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/units")
public class HealthcareUnitController {
    @Autowired
    private HealthcareUnitService healthcareUnitService;

    @GetMapping
    public List<HealthcareUnitDTO> findAll(){
        List<HealthcareUnit> units =  healthcareUnitService.findAll();
        return units.stream().map(HealthcareUnitDTO::new).collect(Collectors.toList());
    }

}
