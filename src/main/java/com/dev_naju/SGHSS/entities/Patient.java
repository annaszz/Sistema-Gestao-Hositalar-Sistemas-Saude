package com.dev_naju.SGHSS.entities;

import com.dev_naju.SGHSS.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tb_patient")
@PrimaryKeyJoinColumn(name = "id")
public class Patient extends UserSystem{
    private String name;
    private String gender;

    @Temporal(TemporalType.DATE)
    private LocalDate dateBirth;

    @Embedded
    private Address address;

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private RecordPatient recordPatient;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true ,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Consultation> consultationList;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Hospitalization> hospitalizations;

    public Patient(){}

    public Patient(String login, String password, String name, String gender, LocalDate dateBirth, Address address) {
        super(login, password, UserRole.PATIENT);
        this.name = name;
       this.gender = gender;
       this.dateBirth  = dateBirth;
       this.address = address;
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

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(LocalDate dateBirth) {
        this.dateBirth = dateBirth;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public RecordPatient getRecord() {
        return recordPatient;
    }

    public void setRecord(RecordPatient recordPatient) {
        this.recordPatient = recordPatient;
        if (recordPatient != null && recordPatient.getPatient() != this) {
            recordPatient.setPatient(this);
        }
    }

    public List<Consultation> getConsultationList() {
        return consultationList;
    }

    public void setConsultationList(List<Consultation> consultationList) {
        this.consultationList = consultationList;
    }

    public List<Hospitalization> getHospitalizations() {
        return hospitalizations;
    }

    public void setHospitalizations(List<Hospitalization> hospitalizations) {
        this.hospitalizations = hospitalizations;
    }
}
