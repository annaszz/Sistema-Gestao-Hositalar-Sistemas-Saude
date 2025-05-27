package com.dev_naju.SGHSS.dto;

import com.dev_naju.SGHSS.entities.Bed;

public class BedSimpleDTO {
    private Long id;
    private String status;
    private String type;
    private int number;

    public BedSimpleDTO(Bed bed) {
        id = bed.getId();
        status = bed.getStatus();
        type = bed.getType();
        number = bed.getNumber();
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
}
