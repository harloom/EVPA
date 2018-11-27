package com.example.hx_loom.evpa;

import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class MusicHiburanActivity extends AppCompatActivity {
    private static final  String isPlaying = "Media is Playing";

    //Deklarasi variabel button
//    private MediaPlayer player;
    private Button doRendahButton;
    private Button reButton;
    private Button miButton;
    private Button faButton;
    private Button soButton;
    private Button laButton;
    private Button siButton;
    private Button doTinggiButton;

    private SoundPool soundPool1;
    int soundID1,soundID2,soundID3,soundID4,soundID5,soundID6,soundID7,soundID8;
    boolean plays = false, loaded = false;
    float actVolume, maxVolume, volume;
    AudioManager audioManager;

    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_hiburan);

        // AudioManager audio settings for adjusting the volume
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        actVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume = actVolume / maxVolume;

        counter = 0;

        //Hardware buttons setting to adjust the media sound
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        //load music first
        soundPool1 = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundID1 = soundPool1.load(this, R.raw.nada_do_rendah, 1);
        soundID2 = soundPool1.load(this, R.raw.re, 2);

        soundID3 = soundPool1.load(this, R.raw.mi, 3);

        soundID4 = soundPool1.load(this, R.raw.fa, 4);

        soundID5 = soundPool1.load(this, R.raw.so, 2);

        soundID6 = soundPool1.load(this, R.raw.la, 2);

        soundID7 = soundPool1.load(this, R.raw.si, 2);
        soundID8 = soundPool1.load(this, R.raw.do_tinggi, 2);



        //Pemanggilan ID button do
        doRendahButton = (Button)
                this.findViewById(R.id.nada_do_awal);
        doRendahButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(1);
            }
        });

        //Pemanggilan ID button re
        reButton = (Button)
                this.findViewById(R.id.nada_re);
        reButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(2);
            }
        });

        //Pemanggilan ID button mi
        miButton = (Button)
                this.findViewById(R.id.nada_mi);
        miButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(3);
            }
        });

        //Pemanggilan ID button fa
        faButton = (Button)
                this.findViewById(R.id.nada_fa);
        faButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(4);
            }
        });

        //Pemanggilan ID button so
        soButton = (Button)
                this.findViewById(R.id.nada_so);
        soButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(5);
            }
        });

        //Pemanggilan ID button la
        laButton = (Button)
                this.findViewById(R.id.nada_la);
        laButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(6);
            }
        });

        //Pemanggilan ID button si
        siButton = (Button)
                this.findViewById(R.id.nada_si);
        siButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(7);
            }
        });

        //Pemanggilan ID button do
        doTinggiButton = (Button)
                this.findViewById(R.id.nada_do_tinggi);
        doTinggiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(8);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    //Method Play
    private void playSound(int arg){





        if (arg == 1){
            //Toast.makeText(this, isPlaying+" do rendah", Toast.LENGTH_LONG).show();
            soundPool1.play(soundID1, volume, volume, 1, 0, 1f);


        }
         if(arg == 2){

             soundPool1.play(soundID2, volume, volume, 1, 0, 1f);

        }
         if(arg == 3) {

             soundPool1.play(soundID3, volume, volume, 1, 0, 1f);

         }
         if(arg == 4) {
             soundPool1.play(soundID4, volume, volume, 1, 0, 1f);

         }
         if(arg == 5) {
             soundPool1.play(soundID5, volume, volume, 1, 0, 1f);

         }
         if(arg == 6) {

             soundPool1.play(soundID6, volume, volume, 1, 0, 1f);

         }
         if(arg == 7) {

             soundPool1.play(soundID7, volume, volume, 1, 0, 1f);

         }
         if(arg == 8) {
             soundPool1.play(soundID8, volume, volume, 1, 0, 1f);
         }

    }
}


