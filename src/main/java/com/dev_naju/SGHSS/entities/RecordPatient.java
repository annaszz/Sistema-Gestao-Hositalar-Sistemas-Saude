package com.dev_naju.SGHSS.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tb_record")
public class RecordPatient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date openDate;

//    private List<String> medicalHistory;

    @OneToOne
    @JoinColumn(name = "patient_id", unique = true, nullable = false)
    @JsonBackReference
    private Patient patient;

    public RecordPatient(){
        this.openDate = new Date();
    }

    public RecordPatient(Date openDate, Patient patient) {
        this.openDate = openDate;
        this.patient = patient;

        if (patient != null && patient.getRecord() != this) {
            patient.setRecord(this);
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RecordPatient recordPatient = (RecordPatient) o;
        return Objects.equals(id, recordPatient.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
