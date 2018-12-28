package com.example.hx_loom.evpa.Report;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hx_loom.evpa.R;
import com.example.hx_loom.evpa.Report.Model.ModelReport;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.Calendar;

public class Report extends AppCompatActivity {
private Boolean spam = false,fake =false,kekerasan =false;
    private  String  intentId;
    private EditText r_text;
    private  Calendar idCalender = Calendar.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        /*firebasefirestore setting*/
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        /*     */

        getIncommingIntent();
        final CardView toogleSpam = (CardView)   findViewById(R.id.textSpam) ;
        final CardView toogleFake = (CardView) findViewById(R.id.textAcaraPalsu);
        final CardView tooglekekerasan  = (CardView) findViewById(R.id.textKekarasan);
        final Button buttonLapor = (Button) findViewById(R.id.button_lapor);
        r_text = (EditText) findViewById(R.id.lapor_text);


        toogleSpam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!spam){
                    spam = true;
                    toogleSpam.setCardBackgroundColor(getResources().getColor(R.color.colorSilver));
                }else{
                    spam = false;
                    toogleSpam.setCardBackgroundColor(getResources().getColor(R.color.white));
                }
            }
        });

        toogleFake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!fake){
                    fake = true;
                    toogleFake.setCardBackgroundColor(getResources().getColor(R.color.colorSilver));
                }else{
                    fake = false;
                    toogleFake.setCardBackgroundColor(getResources().getColor(R.color.white));
                }
            }
        });

        tooglekekerasan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!kekerasan){
                    kekerasan = true;
                    tooglekekerasan.setCardBackgroundColor(getResources().getColor(R.color.colorSilver));
                }else{
                    kekerasan = false;
                    tooglekekerasan.setCardBackgroundColor(getResources().getColor(R.color.white));
                }
            }
        });


        buttonLapor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f_rapor();
            }
        });

    }

    public void backMenu(View view) {
        finish();
    }


    private void f_rapor(){
        int tahun = idCalender.get(Calendar.YEAR);
        int bulan = idCalender.get(Calendar.MONTH);
        int day = idCalender.get(Calendar.DAY_OF_MONTH);
        int hours = idCalender.get(Calendar.HOUR_OF_DAY);
        int minute = idCalender.get(Calendar.MINUTE);
        int second = idCalender.get(Calendar.SECOND);
        final String idDoc = "R-" + tahun +"-" +"-" + bulan +"-" + day + "-" +hours + "-" +minute +"-" + second;

        ModelReport modelReport = new ModelReport(intentId,r_text.getText().toString(),fake,spam,kekerasan);
        db.collection("Report").document(idDoc).set(modelReport).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                toastMessage("Laporan Berhasil Terkirim");
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                toastMessage("Terjadi Kesalahan Silahkan Restart App");
            }
        });

    }

    private void toastMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void  getIncommingIntent(){
        Intent intent = getIntent();
        intentId = intent.getStringExtra("idEvent");
    }

}
