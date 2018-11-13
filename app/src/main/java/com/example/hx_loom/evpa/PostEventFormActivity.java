package com.example.hx_loom.evpa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PostEventFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_event_form);
    }

    public void backMenu(View view) {
        finish();
    }

    @Override
    public boolean isDestroyed() {
        return super.isDestroyed();
    }
}
