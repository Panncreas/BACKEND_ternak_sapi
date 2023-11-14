package com.ternak.sapi.payload.geoloc;

import javax.persistence.Id;

public class GeolocRequest {
    private String latitude;
    private String longitude;

    

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
