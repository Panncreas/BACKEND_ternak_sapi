package com.ternak.sapi.payload.pengobatan;

import com.ternak.sapi.payload.pengobatan.*;
import java.time.Instant;
import javax.validation.constraints.NotBlank;


public class PengobatanResponse {
    private String idKasus;
    private String tanggalPengobatan;
    private String tanggalKasus;
    private String namaPetugas;
    private String namaInfrastruktur;
    private String lokasi;
    private String dosis;
    private String sindrom;
    private String diagnosaBanding;
    private Instant updatedAt;
    private Instant createdAt;

    public String getIdKasus() {
        return idKasus;
    }

    public void setIdKasus(String idKasus) {
        this.idKasus = idKasus;
    }

    public String getTanggalPengobatan() {
        return tanggalPengobatan;
    }

    public void setTanggalPengobatan(String tanggalPengobatan) {
        this.tanggalPengobatan = tanggalPengobatan;
    }

    public String getTanggalKasus() {
        return tanggalKasus;
    }

    public void setTanggalKasus(String tanggalKasus) {
        this.tanggalKasus = tanggalKasus;
    }

    public String getNamaPetugas() {
        return namaPetugas;
    }

    public void setNamaPetugas(String namaPetugas) {
        this.namaPetugas = namaPetugas;
    }

    public String getNamaInfrastruktur() {
        return namaInfrastruktur;
    }

    public void setNamaInfrastruktur(String namaInfrastruktur) {
        this.namaInfrastruktur = namaInfrastruktur;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getSindrom() {
        return sindrom;
    }

    public void setSindrom(String sindrom) {
        this.sindrom = sindrom;
    }

    public String getDiagnosaBanding() {
        return diagnosaBanding;
    }

    public void setDiagnosaBanding(String diagnosaBanding) {
        this.diagnosaBanding = diagnosaBanding;
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
