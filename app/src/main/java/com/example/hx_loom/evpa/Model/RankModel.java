package com.example.hx_loom.evpa.Model;

public class RankModel {
    private String namaEvent;
    private  Integer countSee;
    private  String idEvent;

    public RankModel(String namaEvent, Integer countSee, String idEvent) {
        this.namaEvent = namaEvent;
        this.countSee = countSee;
        this.idEvent = idEvent;
    }

    public String getNamaEvent() {
        return namaEvent;
    }

    public Integer getCountSee() {
        return countSee;
    }

    public String getIdEvent() {
        return idEvent;
    }
}
