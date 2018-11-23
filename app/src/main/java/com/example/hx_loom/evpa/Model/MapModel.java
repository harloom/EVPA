package com.example.hx_loom.evpa.Model;

import com.google.firebase.firestore.GeoPoint;

public class MapModel {

    private String idLokasi;
    private String nameLokasi ;
    private GeoPoint gps;

    public MapModel(String idLokasi, String nameLokasi, GeoPoint gps) {
        this.idLokasi = idLokasi;
        this.nameLokasi = nameLokasi;
        this.gps = gps;
    }

    public String getIdLokasi() {
        return idLokasi;
    }

    public void setIdLokasi(String idLokasi) {
        this.idLokasi = idLokasi;
    }

    public String getNameLokasi() {
        return nameLokasi;
    }

    public void setNameLokasi(String nameLokasi) {
        this.nameLokasi = nameLokasi;
    }

    public GeoPoint getGps() {
        return gps;
    }

    public void setGps(GeoPoint gps) {
        this.gps = gps;
    }


}
