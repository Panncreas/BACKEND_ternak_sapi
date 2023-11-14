
package com.ternak.sapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ternak.sapi.model.audit.UserDateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "geoloc")
public class Geoloc extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String latitude;
    private String longitude;
    
    public Geoloc(){}

    public Geoloc(Long id) {
        this.id = id;
    }
    
    public Geoloc(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    
}
