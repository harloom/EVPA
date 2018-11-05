package com.example.hx_loom.evpa;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private static final String Tag = "DetailActivity" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getIncomingIntent();

    }

    private void getIncomingIntent(){
        Log.d( Tag,"Icoming Intent Form List");
        Intent intent = getIntent();
        String v_judul = intent.getStringExtra("judul");
        String v_detail = intent.getStringExtra("detail_event");

        setToDetail(v_judul , v_detail);

    }

    private void setToDetail(String _vJudul , String _vDetail){
        TextView tx_judul = findViewById(R.id.judul_detail);
        tx_judul.setText(_vJudul);
        TextView tx_detail = findViewById(R.id.desc_detail);
        tx_detail.setText(_vDetail);
    }


    public void onback(View view) {
        finish();
    }
}
