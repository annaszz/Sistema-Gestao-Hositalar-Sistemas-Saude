package com.dev_naju.SGHSS.entities;

import com.dev_naju.SGHSS.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_amin")
@PrimaryKeyJoinColumn(name = "id")
public class Admin extends UserSystem{
    public Admin(){};

    public Admin(String login, String password, String name){
        super(login, password, UserRole.ADMIN);
    }

}
