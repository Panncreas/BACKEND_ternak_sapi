package com.ternak.sapi.payload.pkb;


import com.ternak.sapi.model.Hewan;
import com.ternak.sapi.model.Peternak;
import com.ternak.sapi.payload.kelahiran.*;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

public class PkbRequest {
    @Id
    private String idKejadian;
    private String tanggalPkb;
    private String lokasi;
    private Peternak idPeternak;
    private Hewan kodeEartagNasional;
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

    public Hewan getKodeEartagNasional() {
        return kodeEartagNasional;
    }

    public void setKodeEartagNasional(Hewan kodeEartagNasional) {
        this.kodeEartagNasional = kodeEartagNasional;
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
