package com.ternak.sapi.payload.studyProgram;

import com.ternak.sapi.payload.department.DepartmentResponse;

import java.time.Instant;

public class StudyProgramWithoutDepartmentResponse {
    private String idPeternak;
    private String name;  
    private Long idISIKHNAS;
    private String nikPeternak;
    private String lokasi;
    private String petugasPendaftar;
    private String tanggalPendaftaran;    
    private Instant updatedAt;
    private Instant createdAt;

    public StudyProgramWithoutDepartmentResponse(String idPeternak) {
        this.idPeternak = idPeternak;
    }

    public StudyProgramWithoutDepartmentResponse(String idPeternak, String name, Long idISIKHNAS, String nikPeternak, String lokasi, String petugasPendaftar, String tanggalPendaftaran, Instant updatedAt, Instant createdAt) {
        this.idPeternak = idPeternak;
        this.name = name;
        this.idISIKHNAS = idISIKHNAS;
        this.nikPeternak = nikPeternak;
        this.lokasi = lokasi;
        this.petugasPendaftar = petugasPendaftar;
        this.tanggalPendaftaran = tanggalPendaftaran;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public String getIdPeternak() {
        return idPeternak;
    }

    public void setIdPeternak(String idPeternak) {
        this.idPeternak = idPeternak;
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

    public String getNikPeternak() {
        return nikPeternak;
    }

    public void setNikPeternak(String nikPeternak) {
        this.nikPeternak = nikPeternak;
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
}
