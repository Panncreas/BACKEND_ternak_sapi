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
@Table(name = "peternaks")
public class Peternak extends UserDateAudit {
    @Id
    private String idPeternak;
    
    private String nikPeternak;
    private String namaPeternak;
    
    private String idISIKHNAS;
    
    private String lokasi;
    
    private String petugasPendaftar;
    
    private String tanggalPendaftaran;

    public Peternak() {
    }

    public Peternak(String idPeternak) {
        this.idPeternak = idPeternak;
    }
    
    public Peternak(String idPeternak, String nikPeternak, String namaPeternak, String idISIKHNAS, String lokasi, String petugasPendaftar,String tanggalPendaftaran) {
        this.idPeternak = idPeternak;
        this.nikPeternak = nikPeternak;
        this.namaPeternak = namaPeternak;
        this.idISIKHNAS = idISIKHNAS;
        this.lokasi = lokasi;
        this.petugasPendaftar = petugasPendaftar;
        this.tanggalPendaftaran = tanggalPendaftaran;
    }
    
    public String getNikPeternak() {
        return nikPeternak;
    }

    public void setNikPeternak(String nikPeternak) {
        this.nikPeternak = nikPeternak;
    }

    public void setIdPeternak(String idPeternak) {
        this.idPeternak = idPeternak;
    }

    public String getIdPeternak() {
        return idPeternak;
    }

    public String getNamaPeternak() {
        return namaPeternak;
    }

    public void setNamaPeternak(String namaPeternak) {
        this.namaPeternak = namaPeternak;
    }

    public String getIdISIKHNAS() {
        return idISIKHNAS;
    }

    public void setIdISIKHNAS(String idISIKHNAS) {
        this.idISIKHNAS = idISIKHNAS;
    }

    
    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getPetugasPendaftar() {
        return petugasPendaftar;
    }

    public void setPetugasPendaftar(String petugasPendaftar) {
        this.petugasPendaftar = petugasPendaftar;
    }

    public String getTanggalPendaftaran() {
        return tanggalPendaftaran;
    }

    public void setTanggalPendaftaran(String tanggalPendaftaran) {
        this.tanggalPendaftaran = tanggalPendaftaran;
    }

}
