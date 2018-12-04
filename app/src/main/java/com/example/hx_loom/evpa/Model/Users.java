package com.example.hx_loom.evpa.Model;

import com.google.firebase.Timestamp;

public class Users {
    private String email;
    private  String image_url;
    private  String nama;
    private Timestamp register_timeStamp;

    public Users(String email, String image_url, String nama, Timestamp register_timeStamp) {
        this.email = email;
        this.image_url = image_url;
        this.nama = nama;
        this.register_timeStamp = register_timeStamp;
    }

    public String getEmail() {
        return email;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getNama() {
        return nama;
    }

    public Timestamp getRegister_timeStamp() {
        return register_timeStamp;
    }
}
