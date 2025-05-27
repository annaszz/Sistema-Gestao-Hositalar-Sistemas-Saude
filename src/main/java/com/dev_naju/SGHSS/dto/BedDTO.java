package com.dev_naju.SGHSS.dto;

import com.dev_naju.SGHSS.entities.Bed;
import com.dev_naju.SGHSS.entities.HealthcareUnit;

public class BedDTO {
    private Long id;
    private String status;
    private int number;
    private String type;
    private Long healthcareUnitId;

    public BedDTO(Bed bed) {
        id = bed.getId();
        status = bed.getStatus();
        type = bed.getType();
        number = bed.getNumber();
        healthcareUnitId = bed.getHealthcareUnit().getId();
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getHealthcareUnitId() {
        return healthcareUnitId;
    }

    public void setHealthcareUnitId(Long healthcareUnitId) {
        this.healthcareUnitId = healthcareUnitId;
    }
}
