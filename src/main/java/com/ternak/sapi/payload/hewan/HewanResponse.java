package com.ternak.sapi.payload.hewan;

import com.ternak.sapi.model.Peternak;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;

public class HewanResponse {
    private String kodeEartagNasional;
    private String noKartuTernak;
    private String provinsi;
    private String kabupaten;
    private String kecamatan;
    private String desa;
    private Peternak idPeternak;
    private String spesies;
    private String sex;
    private String umur;
    private String identifikasiHewan;
    private String petugasPendaftar;
    private String tanggalTerdaftar;
    private String fotoHewan;
    private String latitude;
    private String longitude;
    private Instant updatedAt;
    private Instant createdAt;

    public HewanResponse() {
    }
    
    public HewanResponse( String kodeEartagNasional, String noKartuTernakTernak, String provinsi, String kabupaten, String kecamatan,
                        String desa,Peternak idPeternak, String spesies, String sex, String umur, 
                        String identifikasiHewan, String petugasPendaftar, Instant updatedAt, Instant createdAt, String fotoHewan, 
                        String latitude, String longitude) {
        
        
        this.kodeEartagNasional = kodeEartagNasional;
        this.noKartuTernak = noKartuTernakTernak;
        this.provinsi = provinsi;
        this.kabupaten = kabupaten;
        this.kecamatan = kecamatan;
        this.desa = desa;
        this.idPeternak = idPeternak;
        this.spesies = spesies;
        this.sex = sex;
        this.umur = umur;
        this.identifikasiHewan = identifikasiHewan;
        this.petugasPendaftar = petugasPendaftar;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.fotoHewan = fotoHewan;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //SETTER DAN GETTER
    
    public String getNoKartuTernak() {
        return noKartuTernak;
    }
    
    public void setNoKartuTernak(String noKartuTernak) {
        this.noKartuTernak = noKartuTernak;
    }

    public String getKodeEartagNasional() {
        return kodeEartagNasional;
    }

    public void setKodeEartagNasional(String kodeEartagNasional) {
        this.kodeEartagNasional = kodeEartagNasional;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKabupaten() {
        return kabupaten;
    }

    public void setKabupaten(String kabupaten) {
        this.kabupaten = kabupaten;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getDesa() {
        return desa;
    }

    public void setDesa(String desa) {
        this.desa = desa;
    }

    public Peternak getIdPeternak() {
        return idPeternak;
    }

    public void setIdPeternak(Peternak idPeternak) {
        this.idPeternak = idPeternak;
    }

    public String getSpesies() {
        return spesies;
    }

    public void setSpesies(String spesies) {
        this.spesies = spesies;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }

    public String getIdentifikasiHewan() {
        return identifikasiHewan;
    }

    public void setIdentifikasiHewan(String identifikasiHewan) {
        this.identifikasiHewan = identifikasiHewan;
    }

    public String getPetugasPendaftar() {
        return petugasPendaftar;
    }

    public void setPetugasPendaftar(String petugasPendaftar) {
        this.petugasPendaftar = petugasPendaftar;
    }

    public String getTanggalTerdaftar() {
        return tanggalTerdaftar;
    }

    public void setTanggalTerdaftar(String tanggalTerdaftar) {
        this.tanggalTerdaftar = tanggalTerdaftar;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getFotoHewan() {
        return fotoHewan;
    }

    public void setFotoHewan(String fotoHewan) {
        this.fotoHewan = fotoHewan;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    
    
}
