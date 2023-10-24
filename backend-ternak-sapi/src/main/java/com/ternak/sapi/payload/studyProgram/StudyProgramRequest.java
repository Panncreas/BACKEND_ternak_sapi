package com.ternak.sapi.payload.studyProgram;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class StudyProgramRequest {
    //Menambahkan atribut ID
    @Id
    private String idPeternak;
    
    private Long nikPeternak;
    
    @Size(max = 25)
    private String name;

    private Long idISIKHNAS;

    private String lokasi;
    
    private String petugasPendaftar;
    
    private String tanggalPendaftaran;

    //Menambahkan SETTER GETTER ID
    public Long getNikPeternak() {
        return nikPeternak;
    }

    public void setNikPeternak(Long nikPeternak) {
        this.nikPeternak = nikPeternak;
    }
    
    public void setIdPeternak(String idPeternak) {
        this.idPeternak = idPeternak;
    }

    public String getIdPeternak() {
        return idPeternak;
    }
     
      public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIdISIKHNAS() {
        return idISIKHNAS;
    }

    public void setIdISIKHNAS(Long idISIKHNAS) {
        this.idISIKHNAS = idISIKHNAS;
    }


    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getPetugasPendaftar() {
        return petugasPendaftar;
    }

    public void setPetugasPendaftar(String petugasPendaftar) {
        this.petugasPendaftar = petugasPendaftar;
    }

    public String getTanggalPendaftaran() {
        return tanggalPendaftaran;
    }

    public void setTanggalPendaftaran(String tanggalPendaftaran) {
        this.tanggalPendaftaran = tanggalPendaftaran;
    }
}
