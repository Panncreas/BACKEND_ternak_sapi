
package com.ternak.sapi.model;

import com.ternak.sapi.model.audit.UserDateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Entity
@Table(name = "departments")
public class Department extends UserDateAudit {
    @Id
    private String kodeEartagNasional;
    

    @Size(max = 100)
    private String noKartuTernak;

    private String provinsi;
    
    private String kabupaten;
    
    private String kecamatan;
    
    private String desa;
    
    private String namaPeternak;
    
    private String idPeternak;
    
    private String  nikPeternak;
    
    private String spesies;
    
    private String sex;
    
    private String umur;
    
    private String identifikasiHewan;
    
    private String petugasPendaftar;
    
    private String tanggalTerdaftar;
    
    public Department() {
    }

    public Department(String kodeEartagNasional) {
        this.kodeEartagNasional = kodeEartagNasional;
    }

    public Department(String noKartuTernak,String kodeEartagNasional, String provinsi, String kabupaten, String kecamatan,
                        String desa, String namaPeternak, String idPeternak, String nikPeternak, String spesies, String sex, String umur, 
                        String identifikasiHewan, String petugasPendaftar) {
        
        this.noKartuTernak = noKartuTernak;
        this.kodeEartagNasional = kodeEartagNasional;
        this.provinsi = provinsi;
        this.kabupaten = kabupaten;
        this.kecamatan = kecamatan;
        this.desa = desa;
        this.namaPeternak = namaPeternak;
        this.idPeternak = idPeternak;
        this.nikPeternak = nikPeternak;
        this.spesies = spesies;
        this.sex = sex;
        this.umur = umur;
        this.identifikasiHewan = identifikasiHewan;
        this.petugasPendaftar = petugasPendaftar;
    }

    

    public String getNoKartuTernak() {
        return noKartuTernak;
    }

    public void setNoKartuTernak(String noKartuTernak) {
        this.noKartuTernak = noKartuTernak;
    }

    public String getKodeEartagNasional() {
        return kodeEartagNasional;
    }

    public void setKodeEartagNasional(String kodeEartagNasional) {
        this.kodeEartagNasional = kodeEartagNasional;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKabupaten() {
        return kabupaten;
    }

    public void setKabupaten(String kabupaten) {
        this.kabupaten = kabupaten;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getDesa() {
        return desa;
    }

    public void setDesa(String desa) {
        this.desa = desa;
    }

    public String getNamaPeternak() {
        return namaPeternak;
    }

    public void setNamaPeternak(String namaPeternak) {
        this.namaPeternak = namaPeternak;
    }

    public String getIdPeternak() {
        return idPeternak;
    }

    public void setIdPeternak(String idPeternak) {
        this.idPeternak = idPeternak;
    }

    public String getNikPeternak() {
        return nikPeternak;
    }

    public void setNikPeternak(String nikPeternak) {
        this.nikPeternak = nikPeternak;
    }

    public String getSpesies() {
        return spesies;
    }

    public void setSpesies(String spesies) {
        this.spesies = spesies;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }

    public String getIdentifikasiHewan() {
        return identifikasiHewan;
    }

    public void setIdentifikasiHewan(String identifikasiHewan) {
        this.identifikasiHewan = identifikasiHewan;
    }

    public String getPetugasPendaftar() {
        return petugasPendaftar;
    }

    public void setPetugasPendaftar(String petugasPendaftar) {
        this.petugasPendaftar = petugasPendaftar;
    }

    public String getTanggalTerdaftar() {
        return tanggalTerdaftar;
    }

    public void setTanggalTerdaftar(String tanggalTerdaftar) {
        this.tanggalTerdaftar = tanggalTerdaftar;
    }

    
}
