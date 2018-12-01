package com.example.hx_loom.evpa;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firestore.admin.v1beta1.Progress;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

public class DetailActivity extends AppCompatActivity {
    private int[] colorRandom = {
            R.color.purpleHeader,
            R.color.imuto,
            R.color.sadis,
            R.color.sundtere
    };
    private static final String Tag = "DetailActivity" ;
    ProgressBar progress_image ;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        AppBarLayout detailBar = (AppBarLayout) findViewById(R.id.appbar_detail);

        progress_image = (ProgressBar) findViewById(R.id.loading_imageDetai);
        int dice = new Random().nextInt(4);
        detailBar.setBackgroundColor(getResources().getColor(colorRandom[dice]));

        getIncomingIntent();

    }

    private void getIncomingIntent(){
        Log.d( Tag,"Icoming Intent Form List");
        Intent intent = getIntent();
        String v_idEvents = intent.getStringExtra("idEvent");
        String v_judul = intent.getStringExtra("judul");
        String v_detail = intent.getStringExtra("detail_event");
        String v_waktu = intent.getStringExtra("detail_waktu");
        String v_tanggal = intent.getStringExtra("detail_tanggal");
        String v_namaTempat = intent.getStringExtra("detail_namaLokasi");
        ArrayList<String> intentImageUrl = new ArrayList<>(intent.getStringArrayListExtra("arrayImage"));


         DetailData detailData = new DetailData(v_idEvents,v_judul,v_detail,v_waktu,v_tanggal,v_namaTempat,intentImageUrl);
         setToDetail(detailData);

    }

    private void setToDetail(DetailData _detailData) {
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
        final int sizeWidth = findViewById(R.id.imageDetail).getWidth();
        final int sizeHigth = findViewById(R.id.imageDetail).getMinimumHeight() ;
        /* download gambar*/
        StorageReference imageEvents = storageReference.child("Events");
        if(_detailData.getImgUrl().size() != 0) {
            StorageReference idEvents = imageEvents.child(_detailData.v_idEvents + "/" + _detailData.getImgUrl().get(0));
            idEvents.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get()
                            .load(uri)
                            .resize(sizeWidth ,sizeHigth )
                            .centerInside()
                            .placeholder(R.color.colorSilver)
                            .into((ImageView) findViewById(R.id.imageDetail));
                    progress_image.setVisibility(View.INVISIBLE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //erroro
                }
            });
        }else{
            ImageView imageView = (ImageView) findViewById(R.id.imageDetail);
            imageView.setImageResource(R.mipmap.ic_noimage);
            progress_image.setVisibility(View.INVISIBLE);
        }
    }


    public void onback(View view) {
        finish();
    }


}

 class DetailData {

    protected  String v_idEvents,v_judul,v_detail,v_waktu,v_tanggal,
                    v_namaLokasi;
     private ArrayList imgUrl;

     public DetailData(String v_idEvents, String v_judul, String v_detail, String v_waktu, String v_tanggal, String v_namaLokasi, ArrayList imgUrl) {
         this.v_idEvents = v_idEvents;
         this.v_judul = v_judul;
         this.v_detail = v_detail;
         this.v_waktu = v_waktu;
         this.v_tanggal = v_tanggal;
         this.v_namaLokasi = v_namaLokasi;
         this.imgUrl = imgUrl;
     }

     public ArrayList getImgUrl() {
         return imgUrl;
     }
 }
