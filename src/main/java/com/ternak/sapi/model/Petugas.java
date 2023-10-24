package com.ternak.sapi.model;

import com.ternak.sapi.model.audit.UserDateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "petugas")
public class Petugas extends UserDateAudit {
    @Id
    private String nikPetugas;

    private String namaPetugas;
    
    private String noTelp;
    
    private String email;

    public Petugas() {
    }

    public Petugas(String nikPetugas) {
        this.nikPetugas = nikPetugas;
    }
    
    public Petugas(String nikPetugas, String namaPetugas, String noTelp, String email) {
        this.nikPetugas = nikPetugas;
        this.namaPetugas = namaPetugas;
        this.noTelp = noTelp;
        this.email = email;
    }

    public String getNikPetugas() {
        return nikPetugas;
    }

    public void setNikPetugas(String nikPetugas) {
        this.nikPetugas = nikPetugas;
    }

    public String getNamaPetugas() {
        return namaPetugas;
    }

    public void setNamaPetugas(String namaPetugas) {
        this.namaPetugas = namaPetugas;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
}
