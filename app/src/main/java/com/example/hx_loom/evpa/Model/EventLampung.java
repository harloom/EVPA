package com.example.hx_loom.evpa.Model;

import java.util.Date;

public class EventLampung {
    private String idEvent;
    private String namaEvent;
    private String tanggalEvent;
    private String jamEvent;
    private String namaLokasi;
    private String lokasiGPS;
    private String detailEvent;
    private Integer likeEvent;

    public EventLampung(String idEvent, String namaEvent, String tanggalEvent, String jamEvent, String namaLokasi, String lokasiGPS, String detailEvent, Integer likeEvent) {
        this.idEvent = idEvent;
        this.namaEvent = namaEvent;
        this.tanggalEvent = tanggalEvent;
        this.jamEvent = jamEvent;
        this.namaLokasi = namaLokasi;
        this.lokasiGPS = lokasiGPS;
        this.detailEvent = detailEvent;
        this.likeEvent = likeEvent;
    }

    public String getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(String idEvent) {
        this.idEvent = idEvent;
    }

    public String getNamaEvent() {
        return namaEvent;
    }

    public void setNamaEvent(String namaEvent) {
        this.namaEvent = namaEvent;
    }

    public String getTanggalEvent() {
        return tanggalEvent;
    }

    public void setTanggalEvent(String tanggalEvent) {
        this.tanggalEvent = tanggalEvent;
    }

    public String getJamEvent() {
        return jamEvent;
    }

    public void setJamEvent(String jamEvent) {
        this.jamEvent = jamEvent;
    }

    public String getNamaLokasi() {
        return namaLokasi;
    }

    public void setNamaLokasi(String namaLokasi) {
        this.namaLokasi = namaLokasi;
    }

    public String getLokasiGPS() {
        return lokasiGPS;
    }

    public void setLokasiGPS(String lokasiGPS) {
        this.lokasiGPS = lokasiGPS;
    }

    public String getDetailEvent() {
        return detailEvent;
    }

    public void setDetailEvent(String detailEvent) {
        this.detailEvent = detailEvent;
    }

    public Integer getLikeEvent() {
        return likeEvent;
    }

    public void setLikeEvent(Integer likeEvent) {
        this.likeEvent = likeEvent;
    }
}
