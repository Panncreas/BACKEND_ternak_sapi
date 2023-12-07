
package com.ternak.sapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ternak.sapi.model.audit.UserDateAudit;
import javax.persistence.*;

@Entity
@Table(name = "berita")
public class Berita extends UserDateAudit{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBerita;
    
    private String judul;
    private String tglPembuatan;
    private String isiBerita;
    private String pembuat;
    private String fotoBerita;
    private String fotoType;
    
    @Lob
    private byte[] data;

    public Berita() {}

    public Berita(Long idBerita) {
        this.idBerita = idBerita;
    }

    public Berita( String judul, String tglPembuatan, String isiBerita, String pembuat, String fotoBerita, String fotoType, byte[] data) {
        this.judul = judul;
        this.tglPembuatan = tglPembuatan;
        this.isiBerita = isiBerita;
        this.pembuat = pembuat;
        this.fotoBerita =fotoBerita;
        this.fotoType = fotoType;
        this.data = data;
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
