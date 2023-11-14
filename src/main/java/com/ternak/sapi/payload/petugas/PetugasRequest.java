package com.ternak.sapi.payload.petugas;

import javax.persistence.Id;

public class PetugasRequest {
    //Menambahkan atribut ID
    @Id
    private String nikPetugas;
    
    private String namaPetugas;
    
    private String noTelp;
    
    private String email;

    //Menambahkan SETTER GETTER ID
    public String getNikPetugas() {
        return nikPetugas;
    }

    public void setNikPetugas(String nikPetugas) {
        this.nikPetugas = nikPetugas;
    }

    public String getNamaPetugas() {
        return namaPetugas;
    }

    public void setNamaPetugas(String namaPetugas) {
        this.namaPetugas = namaPetugas;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
