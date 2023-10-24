package com.ternak.sapi.payload.pkb;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ternak.sapi.model.Peternak;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class PkbRequest {
    private String idKejadian;
    private String tanggalPkb;
    private String lokasi;
    
    private Peternak idPeternak;
    
    private String idHewan;
    private String spesies;
    private String kategori;
    private String jumlah;
    private String umurKebuntingan;
    private String pemeriksaKebuntingan;

    public String getIdKejadian() {
        return idKejadian;
    }

    public void setIdKejadian(String idKejadian) {
        this.idKejadian = idKejadian;
    }

    public Peternak getIdPeternak() {
        return idPeternak;
    }

    public void setIdPeternak(Peternak idPeternak) {
        this.idPeternak = idPeternak;
    }

   

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    

    public String getIdHewan() {
        return idHewan;
    }

    public void setIdHewan(String idHewan) {
        this.idHewan = idHewan;
    }

    
    public String getPemeriksaKebuntingan() {
        return pemeriksaKebuntingan;
    }

    public void setPemeriksaKebuntingan(String pemeriksaKebuntingan) {
        this.pemeriksaKebuntingan = pemeriksaKebuntingan;
    }

    public String getSpesies() {
        return spesies;
    }

    public void setSpesies(String spesies) {
        this.spesies = spesies;
    }

    public String getTanggalPkb() {
        return tanggalPkb;
    }

    public void setTanggalPkb(String tanggalPkb) {
        this.tanggalPkb = tanggalPkb;
    }

    public String getUmurKebuntingan() {
        return umurKebuntingan;
    }

    public void setUmurKebuntingan(String umurKebuntingan) {
        this.umurKebuntingan = umurKebuntingan;
    }
}
