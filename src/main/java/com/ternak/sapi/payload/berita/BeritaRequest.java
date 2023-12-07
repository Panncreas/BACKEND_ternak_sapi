package com.ternak.sapi.payload.berita;
import javax.persistence.Id;
import org.springframework.web.multipart.MultipartFile;

public class BeritaRequest {
    @Id
    private Long idBerita;
    private String judul;
    private String tglPembuatan;
    private String isiBerita;
    private String pembuat;
    private MultipartFile file;

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

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
