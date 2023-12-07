package com.ternak.sapi.payload.berita;

import java.time.Instant;
import javax.persistence.Lob;


public class BeritaResponse {
    private Long idBerita;
    private String judul;
    private String tglPembuatan;
    private String isiBerita;
    private String pembuat;
    private String fotoBerita;
    private String fotoType;
    
    @Lob
    private byte[] data;
    
    private Instant updatedAt;
    private Instant createdAt;

    public BeritaResponse() {}

    public BeritaResponse(Long idBerita, String judul, String tglPembuatan, String isiBerita, 
            String pembuat, String fotoBerita, String fotoType, byte[] data, Instant updatedAt, Instant createdAt) {
        this.idBerita = idBerita;
        this.judul = judul;
        this.tglPembuatan = tglPembuatan;
        this.isiBerita = isiBerita;
        this.pembuat = pembuat;
        this.fotoBerita = fotoBerita;
        this.fotoType = fotoType;
        this.data = data;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }
    
    
    public Long getIdBerita() {
        return idBerita;
    }

    public void setIdBerita(Long idBerita) {
        this.idBerita = idBerita;
    }
    
    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getTglPembuatan() {
        return tglPembuatan;
    }

    public void setTglPembuatan(String tglPembuatan) {
        this.tglPembuatan = tglPembuatan;
    }

    public String getIsiBerita() {
        return isiBerita;
    }

    public void setIsiBerita(String isiBerita) {
        this.isiBerita = isiBerita;
    }

    public String getPembuat() {
        return pembuat;
    }

    public void setPembuat(String pembuat) {
        this.pembuat = pembuat;
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

    public String getFotoBerita() {
        return fotoBerita;
    }

    public void setFotoBerita(String fotoBerita) {
        this.fotoBerita = fotoBerita;
    }

    public String getFotoType() {
        return fotoType;
    }

    public void setFotoType(String fotoType) {
        this.fotoType = fotoType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
    
    
}
