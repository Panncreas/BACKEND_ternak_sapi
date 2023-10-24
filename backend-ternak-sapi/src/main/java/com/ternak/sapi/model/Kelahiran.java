package com.ternak.sapi.model;

import com.ternak.sapi.model.audit.UserDateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "kelahiran")
public class Kelahiran extends UserDateAudit {
    @Id
    private String idKejadian;

    @Lob
    private String tanggalLaporan;
    private String tanggalLahir;
    private String lokasi;
    private String namaPeternak;
    
    @ManyToOne
    @JoinColumn(name = "idPeternak", referencedColumnName = "idPeternak")
    private StudyProgram idPeternak;
    private String kartuTernakInduk;
    private String eartagInduk;
    private String idHewanInduk;
    private String spesiesInduk;
    private String idPejantanStraw;
    private String idBatchStraw;
    private String produsenStraw;
    private String spesiesPejantan;
    private String jumlah;
    private String kartuTernakAnak;
    private String eartagAnak;
    private String idHewanAnak;
    private String jenisKelaminAnak;
    private String kategori;
    private String petugasPelopor;
    private String urutanIb;

    public Kelahiran() {
    }

    public Kelahiran(String idKejadian) {
        this.idKejadian = idKejadian;
    }

    public Kelahiran( String idKejadian, String tanggalLaporan, String tanggalLahir, String lokasi, 
            String namaPeternak, StudyProgram idPeternak, String kartuTernakInduk, String eartagInduk, String idHewanInduk, 
            String spesiesInduk, String idPejantanStraw, String idBatchStraw, String produsenStraw, String spesiesPejantan, 
            String jumlah, String kartuTernakAnak, String eartagAnak, String idHewanAnak, String jenisKelaminAnak, 
            String kategori, String petugasPelopor, String urutanIb) {
        this.idKejadian = idKejadian;
        this.tanggalLaporan = tanggalLaporan;
        this.tanggalLahir = tanggalLahir;
        this.lokasi = lokasi;
        this.namaPeternak = namaPeternak;
        this.idPeternak = idPeternak;
        this.kartuTernakInduk = kartuTernakInduk;
        this.eartagInduk = eartagInduk;
        this.idHewanInduk = idHewanInduk;
        this.spesiesInduk = spesiesInduk;
        this.idPejantanStraw = idPejantanStraw;
        this.idBatchStraw = idBatchStraw;
        this.produsenStraw = produsenStraw;
        this.spesiesPejantan = spesiesPejantan;
        this.jumlah = jumlah;
        this.kartuTernakAnak = kartuTernakAnak;
        this.eartagAnak = eartagAnak;
        this.idHewanAnak = idHewanAnak;
        this.jenisKelaminAnak = jenisKelaminAnak;
        this.kategori = kategori;
        this.petugasPelopor = petugasPelopor;
        this.urutanIb = urutanIb;
    }
    
    

    public String getEartagAnak() {
        return eartagAnak;
    }

    public void setEartagAnak(String eartagAnak) {
        this.eartagAnak = eartagAnak;
    }

    public String getEartagInduk() {
        return eartagInduk;
    }

    public void setEartagInduk(String eartagInduk) {
        this.eartagInduk = eartagInduk;
    }

    public String getIdBatchStraw() {
        return idBatchStraw;
    }

    public void setIdBatchStraw(String idBatchStraw) {
        this.idBatchStraw = idBatchStraw;
    }

    public String getIdHewanAnak() {
        return idHewanAnak;
    }

    public void setIdHewanAnak(String idHewanAnak) {
        this.idHewanAnak = idHewanAnak;
    }

    public String getIdHewanInduk() {
        return idHewanInduk;
    }

    public void setIdHewanInduk(String idHewanInduk) {
        this.idHewanInduk = idHewanInduk;
    }

    public String getIdKejadian() {
        return idKejadian;
    }

    public void setIdKejadian(String idKejadian) {
        this.idKejadian = idKejadian;
    }

    public String getIdPejantanStraw() {
        return idPejantanStraw;
    }

    public void setIdPejantanStraw(String idPejantanStraw) {
        this.idPejantanStraw = idPejantanStraw;
    }

    public StudyProgram getIdPeternak() {
        return idPeternak;
    }

    public void setIdPeternak(StudyProgram idPeternak) {
        this.idPeternak = idPeternak;
    }

    public String getJenisKelaminAnak() {
        return jenisKelaminAnak;
    }

    public void setJenisKelaminAnak(String jenisKelaminAnak) {
        this.jenisKelaminAnak = jenisKelaminAnak;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getKartuTernakAnak() {
        return kartuTernakAnak;
    }

    public void setKartuTernakAnak(String kartuTernakAnak) {
        this.kartuTernakAnak = kartuTernakAnak;
    }

    public String getKartuTernakInduk() {
        return kartuTernakInduk;
    }

    public void setKartuTernakInduk(String kartuTernakInduk) {
        this.kartuTernakInduk = kartuTernakInduk;
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

    public String getNamaPeternak() {
        return namaPeternak;
    }

    public void setNamaPeternak(String namaPeternak) {
        this.namaPeternak = namaPeternak;
    }

    public String getPetugasPelopor() {
        return petugasPelopor;
    }

    public void setPetugasPelopor(String petugasPelopor) {
        this.petugasPelopor = petugasPelopor;
    }

    public String getProdusenStraw() {
        return produsenStraw;
    }

    public void setProdusenStraw(String produsenStraw) {
        this.produsenStraw = produsenStraw;
    }

    public String getSpesiesInduk() {
        return spesiesInduk;
    }

    public void setSpesiesInduk(String spesiesInduk) {
        this.spesiesInduk = spesiesInduk;
    }

    public String getSpesiesPejantan() {
        return spesiesPejantan;
    }

    public void setSpesiesPejantan(String spesiesPejantan) {
        this.spesiesPejantan = spesiesPejantan;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getTanggalLaporan() {
        return tanggalLaporan;
    }

    public void setTanggalLaporan(String tanggalLaporan) {
        this.tanggalLaporan = tanggalLaporan;
    }

    public String getUrutanIb() {
        return urutanIb;
    }

    public void setUrutanIb(String urutanIb) {
        this.urutanIb = urutanIb;
    }
}
