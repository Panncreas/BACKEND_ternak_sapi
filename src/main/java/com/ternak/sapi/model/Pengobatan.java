package com.ternak.sapi.model;

import com.ternak.sapi.model.audit.UserDateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(name = "pengobatan")
public class Pengobatan extends UserDateAudit {
    @Id
    @Size(max = 100)
    private String idKasus;

    @Lob
    private String tanggalPengobatan;
    private String tanggalKasus;
    private String namaPetugas;
    private String namaInfrastruktur;
    private String lokasi;
    private String dosis;
    private String sindrom;
    private String diagnosaBanding;

    public Pengobatan() {
    }

    public Pengobatan(String idKasus) {
        this.idKasus = idKasus;
    }

    public Pengobatan(String idKasus, String tanggalPengobatan, String tanggalKasus, String namaPetugas,
            String namaInfrastruktur, String lokasi, String dosis, String sindrom, String diagnosaBanding) {
        this.idKasus = idKasus;
        this.tanggalPengobatan = tanggalPengobatan;
        this.tanggalKasus = tanggalKasus;
        this.namaPetugas = namaPetugas;
        this.namaInfrastruktur = namaInfrastruktur;
        this.lokasi = lokasi;
        this.dosis = dosis;
        this.sindrom = sindrom;
        this.diagnosaBanding = diagnosaBanding;
    }

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
}
