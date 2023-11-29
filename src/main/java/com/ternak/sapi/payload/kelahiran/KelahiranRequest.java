package com.ternak.sapi.payload.kelahiran;


import com.ternak.sapi.model.Peternak;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

public class KelahiranRequest {
    @Id
    private String idKejadian;

    private String tanggalLaporan;
    private String tanggalLahir;
    private String lokasi;
    
    private Peternak idPeternak;
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
    private String petugasPelapor;
    private String urutanIb;
    
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

    public Peternak getIdPeternak() {
        return idPeternak;
    }

    public void setIdPeternak(Peternak idPeternak) {
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

    public String getPetugasPelapor() {
        return petugasPelapor;
    }

    public void setPetugasPelapor(String petugasPelapor) {
        this.petugasPelapor = petugasPelapor;
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
