package com.dev_naju.SGHSS.entities;

import com.dev_naju.SGHSS.enums.ProfessionalRole;
import com.dev_naju.SGHSS.enums.UserRole;
import jakarta.persistence.*;


import java.util.*;

@Entity
@Table(name = "tb_professional")
@PrimaryKeyJoinColumn(name = "id")
public class ProfessionalHealth extends UserSystem{
    private String name;
    private String crm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProfessionalRole professionalRole;

    @OneToMany(mappedBy="professional")
    private Set<Consultation> consultations = new HashSet<>();

    public ProfessionalHealth(){}

    public ProfessionalHealth(String login, String password, String name, String crm, ProfessionalRole professionalRole) {
        super(login, password, UserRole.PROFESSIONAL_HEALTH);
        this.name = name;
        this.crm = crm;
        this.professionalRole = professionalRole;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public ProfessionalRole getProfessionalRole() {
        return professionalRole;
    }

    public void setProfessionalRole(ProfessionalRole professionalRole) {
        this.professionalRole = professionalRole;
    }

    public Set<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(Set<Consultation> consultations) {
        this.consultations = consultations;
    }
}
