package com.dev_naju.SGHSS.services;

import com.dev_naju.SGHSS.entities.Bed;
import com.dev_naju.SGHSS.repositories.BedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BedService {
    @Autowired
    private BedRepository bedRepository;

    public List<Bed> findAll(){
        return bedRepository.findAll();
    }


}
