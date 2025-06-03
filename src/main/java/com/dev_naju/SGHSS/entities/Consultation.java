package com.dev_naju.SGHSS.entities;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "tb_consultation")
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String typeConsultation;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="professional_id")
    private ProfessionalHealth professional;

    public Consultation(){}

    public Consultation(String typeConsultation, Date date, Patient patient) {
        this.typeConsultation = typeConsultation;
        this.date = date;
        this.patient = patient;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
