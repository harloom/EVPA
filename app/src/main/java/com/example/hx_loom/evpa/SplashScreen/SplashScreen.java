package com.example.hx_loom.evpa.SplashScreen;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.example.hx_loom.evpa.MainActivity;
import com.example.hx_loom.evpa.R;

public class SplashScreen extends AppCompatActivity {
//    private ImageView logo;
    private static int splashTime = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


            new Handler().postDelayed(new Thread() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    SplashScreen.this.startActivity(intent);
                    SplashScreen.this.finish();
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                }
            }, splashTime);
        }
    }

