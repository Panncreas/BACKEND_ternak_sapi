
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

    public Berita() {}

    public Berita(Long idBerita) {
        this.idBerita = idBerita;
    }

    public Berita( String judul, String tglPembuatan, String isiBerita, String pembuat) {
        this.judul = judul;
        this.tglPembuatan = tglPembuatan;
        this.isiBerita = isiBerita;
        this.pembuat = pembuat;
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
    
    
}
