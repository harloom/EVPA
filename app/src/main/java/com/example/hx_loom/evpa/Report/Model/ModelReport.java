package com.example.hx_loom.evpa.Report.Model;

public class ModelReport {
    private String idEvents;
    private  String  textLapor;
    private Boolean acaraPalsu,Spam,Kekerasan;

    public ModelReport(String idEvents, String textLapor, Boolean acaraPalsu, Boolean spam, Boolean kekerasan) {
        this.idEvents = idEvents;
        this.textLapor = textLapor;
        this.acaraPalsu = acaraPalsu;
        Spam = spam;
        Kekerasan = kekerasan;
    }

    public String getIdEvents() {
        return idEvents;
    }

    public String getTextLapor() {
        return textLapor;
    }

    public Boolean getAcaraPalsu() {
        return acaraPalsu;
    }

    public Boolean getSpam() {
        return Spam;
    }

    public Boolean getKekerasan() {
        return Kekerasan;
    }
}
