package com.example.hx_loom.evpa.Model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.List;

public class AddEventModel {
    private String idUsers;
    private String namaEvent;
    private String desEvent;
    private String namaLokasi;
    private GeoPoint lokasiGps;
    private String date;
    private String time;
    private ArrayList imgUrl;

    public AddEventModel(String idUsers, String namaEvent, String desEvent, String namaLokasi, GeoPoint lokasiGps, String date, String time, ArrayList imgUrl) {
        this.idUsers = idUsers;
        this.namaEvent = namaEvent;
        this.desEvent = desEvent;
        this.namaLokasi = namaLokasi;
        this.lokasiGps = lokasiGps;
        this.date = date;
        this.time = time;
        this.imgUrl = imgUrl;
    }

    public String getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(String idUsers) {
        this.idUsers = idUsers;
    }

    public String getNamaEvent() {
        return namaEvent;
    }

    public void setNamaEvent(String namaEvent) {
        this.namaEvent = namaEvent;
    }

    public String getDesEvent() {
        return desEvent;
    }

    public void setDesEvent(String desEvent) {
        this.desEvent = desEvent;
    }

    public String getNamaLokasi() {
        return namaLokasi;
    }

    public void setNamaLokasi(String namaLokasi) {
        this.namaLokasi = namaLokasi;
    }

    public GeoPoint getLokasiGps() {
        return lokasiGps;
    }

    public void setLokasiGps(GeoPoint lokasiGps) {
        this.lokasiGps = lokasiGps;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public ArrayList getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(ArrayList imgUrl) {
        this.imgUrl = imgUrl;
    }
}
