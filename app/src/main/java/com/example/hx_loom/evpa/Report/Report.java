package com.example.hx_loom.evpa.Report;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.hx_loom.evpa.R;

public class Report extends AppCompatActivity {
private Boolean spam = false,fake =false,kekerasan =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        final CardView toogleSpam = (CardView)   findViewById(R.id.textSpam) ;
        final CardView toogleFake = (CardView) findViewById(R.id.textAcaraPalsu);
        final CardView tooglekekerasan  = (CardView) findViewById(R.id.textKekarasan);
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

    }

    public void backMenu(View view) {
        finish();
    }




}
