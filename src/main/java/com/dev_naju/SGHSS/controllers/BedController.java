package com.dev_naju.SGHSS.controllers;

import com.dev_naju.SGHSS.dto.BedDTO;
import com.dev_naju.SGHSS.dto.HealthcareUnitDTO;
import com.dev_naju.SGHSS.entities.Bed;
import com.dev_naju.SGHSS.services.BedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

//somente admins
@RestController
@RequestMapping("/units/beds")
public class BedController {
    @Autowired
    private BedService bedService;

    @GetMapping
    public List<BedDTO> findAll() {
        List<Bed> beds = bedService.findAll();
        return beds.stream().map(this::convertToBedDTO).collect(Collectors.toList());
    }

    @PostMapping("/hospitalization")
    public RequestBody hospitalizations(@RequestBody HealthcareUnitDTO data){
        return null;
    }

    private BedDTO convertToBedDTO(Bed bed) {
        return new BedDTO(
                bed.getId(),
                bed.getStatus(),
                bed.getNumber(),
                bed.getHealthcareUnit() != null ? bed.getHealthcareUnit().getId() : null
        );
    } //nem preciso disso

}
