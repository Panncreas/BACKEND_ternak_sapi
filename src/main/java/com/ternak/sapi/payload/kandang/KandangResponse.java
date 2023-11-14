package com.ternak.sapi.payload.kandang;

import com.ternak.sapi.model.Peternak;
import java.time.Instant;

public class KandangResponse {
    private String idKandang;
    
    private Peternak idPeternak;
    private String luas;
    private String kapasitas;
    private String nilaiBangunan;
    private String alamat;
    private String desa;
    private String kecamatan;
    private String kabupaten;
    private String provinsi;
    private String fotoKandang;
    private String latitude;
    private String longitude;
    private Instant updatedAt;
    private Instant createdAt;

    public KandangResponse() {}

    public KandangResponse(String idKandang, Peternak idPeternak, String luas, String kapasitas, String nilaiBangunan, 
            String alamat, String desa, String kecamatan, String kabupaten, String provinsi,
            String fotoKandang, Instant updatedAt, Instant createdAt, String latitude, String longitude) {
        this.idKandang = idKandang;
        this.idPeternak = idPeternak;
        this.luas = luas;
        this.kapasitas = kapasitas;
        this.nilaiBangunan = nilaiBangunan;
        this.alamat = alamat;
        this.desa = desa;
        this.kecamatan = kecamatan;
        this.kabupaten = kabupaten;
        this.provinsi = provinsi;
        this.fotoKandang = fotoKandang;
        this.latitude = latitude;
        this.longitude = longitude;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }
    

    
    public String getIdKandang() {
        return idKandang;
    }

    public void setIdKandang(String idKandang) {
        this.idKandang = idKandang;
    }

    public Peternak getIdPeternak() {
        return idPeternak;
    }

    public void setIdPeternak(Peternak idPeternak) {
        this.idPeternak = idPeternak;
    }

    public String getLuas() {
        return luas;
    }

    public void setLuas(String luas) {
        this.luas = luas;
    }

    public String getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(String kapasitas) {
        this.kapasitas = kapasitas;
    }

    public String getNilaiBangunan() {
        return nilaiBangunan;
    }

    public void setNilaiBangunan(String nilaiBangunan) {
        this.nilaiBangunan = nilaiBangunan;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getDesa() {
        return desa;
    }

    public void setDesa(String desa) {
        this.desa = desa;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getKabupaten() {
        return kabupaten;
    }

    public void setKabupaten(String kabupaten) {
        this.kabupaten = kabupaten;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getFotoKandang() {
        return fotoKandang;
    }

    public void setFotoKandang(String fotoKandang) {
        this.fotoKandang = fotoKandang;
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
