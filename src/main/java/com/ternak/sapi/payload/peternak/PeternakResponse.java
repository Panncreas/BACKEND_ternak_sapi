package com.ternak.sapi.payload.peternak;

import com.ternak.sapi.model.Hewan;
import com.ternak.sapi.payload.hewan.HewanResponse;

import java.time.Instant;

public class PeternakResponse {
    private String idPeternak;
    private String nikPeternak;
    private String namaPeternak;
    private String idISIKHNAS;
    private String lokasi;
    private String petugasPendaftar;
    private String tanggalPendaftaran;
    private Instant updatedAt;
    private Instant createdAt;

    
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
