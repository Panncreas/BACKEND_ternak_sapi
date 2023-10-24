package com.ternak.sapi.payload.studyProgram;

import com.ternak.sapi.model.Department;
import com.ternak.sapi.payload.department.DepartmentResponse;

import java.time.Instant;

public class StudyProgramResponse {
    private String idPeternak;
    private Long nikPeternak;
    private String name;
    private Long idISIKHNAS;
    private String lokasi;
    private String petugasPendaftar;
    private String tanggalPendaftaran;
    private Instant updatedAt;
    private Instant createdAt;

    
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

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
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
