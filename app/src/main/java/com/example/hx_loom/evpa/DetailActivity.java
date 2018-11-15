package com.example.hx_loom.evpa;

import android.annotation.TargetApi;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.Random;

public class DetailActivity extends AppCompatActivity {
    private int[] colorRandom = {
            R.color.purpleHeader,
            R.color.imuto,
            R.color.sadis,
            R.color.sundtere
    };
    private static final String Tag = "DetailActivity" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        AppBarLayout detailBar = (AppBarLayout) findViewById(R.id.appbar_detail);

        int dice = new Random().nextInt(4);
        detailBar.setBackgroundColor(getResources().getColor(colorRandom[dice]));

        getIncomingIntent();

    }

    private void getIncomingIntent(){
        Log.d( Tag,"Icoming Intent Form List");
        Intent intent = getIntent();
        String v_judul = intent.getStringExtra("judul");
        String v_detail = intent.getStringExtra("detail_event");
        String v_waktu = intent.getStringExtra("detail_waktu");
        String v_tanggal = intent.getStringExtra("detail_tanggal");
        String v_namaTempat = intent.getStringExtra("detail_namaLokasi");

         DetailData detailData = new DetailData(v_judul,v_detail,v_waktu,v_tanggal,v_namaTempat);
         setToDetail(detailData);

    }

    private void setToDetail(DetailData _detailData){
        TextView tx_judul = findViewById(R.id.judul_detail);
        tx_judul.setText((String) _detailData.v_judul);
        TextView tx_detail = findViewById(R.id.desc_detail);
        tx_detail.setText((String) _detailData.v_detail);
        TextView tx_waktu = findViewById(R.id.waktu_detail);
        tx_waktu.setText((String) _detailData.v_waktu);
        TextView tx_tanggal = findViewById(R.id.tanggal_detail);
        tx_tanggal.setText((String) _detailData.v_tanggal);
        TextView tx_namaTempat = findViewById(R.id.namaTempat_detail);
        tx_namaTempat.setText((String) _detailData.v_namaLokasi);
    }


    public void onback(View view) {
        finish();
    }


}

 class DetailData {

    protected  String v_judul,v_detail,v_waktu,v_tanggal,
                    v_namaLokasi;

    public DetailData(String v_judul , String v_detail, String v_waktu, String v_tanggal,String v_namaLokasi) {
        this.v_judul = v_judul;
        this.v_detail = v_detail;
        this.v_waktu = v_waktu;
        this.v_tanggal = v_tanggal;
        this.v_namaLokasi = v_namaLokasi;
    }

//    void DetailData() {
//
//    }

//    public String getV_judul() {
//        return v_judul;
//    }
//
//    public void setV_judul(String v_judul) {
//        this.v_judul = v_judul;
//    }
//
//    public String getV_detail() {
//        return v_detail;
//    }
//
//    public void setV_detail(String v_detail) {
//        this.v_detail = v_detail;
//    }
//
//    public String getV_waktu() {
//        return v_waktu;
//    }
//
//    public void setV_waktu(String v_waktu) {
//        this.v_waktu = v_waktu;
//    }
//
//    public String getV_tanggal() {
//        return v_tanggal;
//    }
//
//    public void setV_tanggal(String v_tanggal) {
//        this.v_tanggal = v_tanggal;
//    }
//
//     public String getV_namaLokasi() {
//         return v_namaLokasi;
//     }
//
//     public void setV_namaLokasi(String v_namaLokasi) {
//         this.v_namaLokasi = v_namaLokasi;
//     }
 }
