package com.dev_naju.SGHSS.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="tb_healthcare_unit")
public class HealthcareUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String unitType;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "healthcareUnit")
    private Set<Bed> beds = new HashSet<>();

    public HealthcareUnit(){}

    public HealthcareUnit(Long id, String name, String unitType, Address address) {
        this.id = id;
        this.name = name;
        this.unitType = unitType;
        this.address = address;
    }

    public HealthcareUnit(String name, String unitType) {
        this.name = name;
        this.unitType = unitType;
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

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Bed> getBeds() {
        return beds;
    }

    public void setBeds(Set<Bed> beds) {
        this.beds = beds;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        HealthcareUnit that = (HealthcareUnit) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
