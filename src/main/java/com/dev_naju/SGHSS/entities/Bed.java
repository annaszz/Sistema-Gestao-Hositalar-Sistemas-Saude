package com.dev_naju.SGHSS.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="tb_bed")

public class Bed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;
    private int number;

    @ManyToOne
    @JoinColumn(name="healthcare_unit_id")
    private HealthcareUnit healthcareUnit;

    public Bed(){}

    public Bed(String status, int number, HealthcareUnit healthcareUnit) {
        this.status = status;
        this.number = number;
        this.healthcareUnit = healthcareUnit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public HealthcareUnit getHealthcareUnit() {
        return healthcareUnit;
    }

    public void setHealthcareUnit(HealthcareUnit healthcareUnit) {
        this.healthcareUnit = healthcareUnit;
    }
}
