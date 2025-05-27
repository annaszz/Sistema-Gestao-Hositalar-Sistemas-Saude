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
    private String type;
    private int number;

    @ManyToOne
    @JoinColumn(name="healthcare_unit_id")
    private HealthcareUnit healthcareUnit;

    public Bed(){}

    public Bed(Long id, String status, String type, int number, HealthcareUnit healthcareUnit) {
        this.id = id;
        this.status = status;
        this.type = type;
        this.number = number;
        this.healthcareUnit = healthcareUnit;
    }

    public Bed(String status, String type, int number, HealthcareUnit healthcareUnit) {
        this.status = status;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Bed bed = (Bed) o;
        return Objects.equals(id, bed.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
