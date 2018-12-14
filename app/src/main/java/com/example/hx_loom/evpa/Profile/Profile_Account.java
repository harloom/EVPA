package com.example.hx_loom.evpa.Profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.hx_loom.evpa.R;

public class Profile_Account extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__account);
    }

    public void backMenu(View view) {
        finish();
    }
}
