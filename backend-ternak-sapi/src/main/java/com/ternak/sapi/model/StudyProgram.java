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
@Table(name = "study_programs")
public class StudyProgram extends UserDateAudit {
    @Id
    private String idPeternak;
    
    private Long nikPeternak;
    
    @Size(max = 25)
    private String name;
    
    private Long idISIKHNAS;
    
    private String lokasi;
    
    private String petugasPendaftar;
    
    private String tanggalPendaftaran;

    public StudyProgram() {
    }

    public StudyProgram(String idPeternak) {
        this.idPeternak = idPeternak;
    }
    
    public StudyProgram(String idPeternak, Long nikPeternak, String name, Long idISIKHNAS, String lokasi, String petugasPendaftar,String tanggalPendaftaran) {
        this.idPeternak = idPeternak;
        this.nikPeternak = nikPeternak;
        this.name = name;
        this.idISIKHNAS = idISIKHNAS;
        this.lokasi = lokasi;
        this.petugasPendaftar = petugasPendaftar;
        this.tanggalPendaftaran = tanggalPendaftaran;
    }
    
    public Long getNikPeternak() {
        return nikPeternak;
    }

    public void setNikPeternak(Long nikPeternak) {
        this.nikPeternak = nikPeternak;
    }

    public void setIdPeternak(String idPeternak) {
        this.idPeternak = idPeternak;
    }

    public String getIdPeternak() {
        return idPeternak;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIdISIKHNAS() {
        return idISIKHNAS;
    }

    public void setIdISIKHNAS(Long idISIKHNAS) {
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
