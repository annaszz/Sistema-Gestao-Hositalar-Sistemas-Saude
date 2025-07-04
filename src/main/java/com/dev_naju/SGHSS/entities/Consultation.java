package com.dev_naju.SGHSS.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "tb_consultation")
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String typeConsultation;

    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="patient_id", nullable = false)
    @JsonBackReference
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="professional_id")
    private ProfessionalHealth professional;

    public Consultation(){}

    public Consultation(String typeConsultation, LocalDateTime date, Patient patient, ProfessionalHealth professional) {
        this.typeConsultation = typeConsultation;
        this.date = date;
        this.patient = patient;
        this.professional = professional;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeConsultation() {
        return typeConsultation;
    }

    public void setTypeConsultation(String typeConsultation) {
        this.typeConsultation = typeConsultation;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public ProfessionalHealth getProfessional() {
        return professional;
    }

    public void setProfessional(ProfessionalHealth professional) {
        this.professional = professional;
    }
}
