package com.dev_naju.SGHSS.dto;

import com.dev_naju.SGHSS.entities.*;
import com.dev_naju.SGHSS.entities.Record;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatientDTO {
    private Long id;
    private String name;
    private String gender;
    private Address address;
    private Date dateBirth;
    private Record record;

    public PatientDTO(Patient patient) {
        id = patient.getId();
        name = patient.getName();
        gender = patient.getGender();
        address = patient.getAddress();
        dateBirth = patient.getDateBirth();
        record = patient.getRecord();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }
}
