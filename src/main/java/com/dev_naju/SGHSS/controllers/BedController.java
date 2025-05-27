package com.dev_naju.SGHSS.controllers;

import com.dev_naju.SGHSS.dto.BedDTO;
import com.dev_naju.SGHSS.entities.Bed;
import com.dev_naju.SGHSS.services.BedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bed")
public class BedController {
    @Autowired
    private BedService bedService;

    @GetMapping
    public List<BedDTO> findAll() {
        List<Bed> beds = bedService.findAll();
        return beds.stream().map(BedDTO::new).collect(Collectors.toList());
    }

}
