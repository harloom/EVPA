package com.example.hx_loom.evpa;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.hx_loom.evpa.FunctionRank.Model.ModelCount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firestore.admin.v1beta1.Progress;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

import javax.annotation.Nullable;

public class DetailActivity extends AppCompatActivity {
    private int[] colorRandom = {
            R.color.purpleHeader,
            R.color.imuto,
            R.color.sadis,
            R.color.sundtere
    };
    private static final String Tag = "DetailActivity";
    ProgressBar progress_image;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();

        AppBarLayout detailBar = findViewById(R.id.appbar_detail);

        progress_image = findViewById(R.id.loading_imageDetai);
        int dice = new Random().nextInt(4);
        detailBar.setBackgroundColor(getResources().getColor(colorRandom[dice]));

        getIncomingIntent();
        getCountEventLoad();
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    Intent intent;
    String v_idEvents;

    private void getIncomingIntent() {
        Log.d(Tag, "Icoming Intent Form List");
        intent = getIntent();
        v_idEvents = intent.getStringExtra("idEvent");
        String v_judul = intent.getStringExtra("judul");
        String v_detail = intent.getStringExtra("detail_event");
        String v_waktu = intent.getStringExtra("detail_waktu");
        String v_tanggal = intent.getStringExtra("detail_tanggal");
        String v_namaTempat = intent.getStringExtra("detail_namaLokasi");
        ArrayList<String> intentImageUrl = new ArrayList<>(intent.getStringArrayListExtra("arrayImage"));


        DetailData detailData = new DetailData(v_idEvents, v_judul, v_detail, v_waktu, v_tanggal, v_namaTempat, intentImageUrl);
        setToDetail(detailData);

    }

    private void setToDetail(DetailData _detailData) {
        TextView tx_judul = findViewById(R.id.judul_detail);
        tx_judul.setText(_detailData.v_judul);
        TextView tx_detail = findViewById(R.id.desc_detail);
        tx_detail.setText(_detailData.v_detail);
        TextView tx_waktu = findViewById(R.id.waktu_detail);
        tx_waktu.setText(_detailData.v_waktu);
        TextView tx_tanggal = findViewById(R.id.tanggal_detail);
        tx_tanggal.setText(_detailData.v_tanggal);
        TextView tx_namaTempat = findViewById(R.id.namaTempat_detail);
        tx_namaTempat.setText(_detailData.v_namaLokasi);
        final int sizeWidth = findViewById(R.id.imageDetail).getWidth();
        final int sizeHigth = findViewById(R.id.imageDetail).getMinimumHeight();
        /* download gambar*/
        StorageReference imageEvents = storageReference.child("Events");
        if (_detailData.getImgUrl().size() != 0) {
            StorageReference idEvents = imageEvents.child(_detailData.v_idEvents + "/" + _detailData.getImgUrl().get(0));
            idEvents.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get()
                            .load(uri)
                            .resize(sizeWidth, sizeHigth)
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
        } else {
            ImageView imageView = findViewById(R.id.imageDetail);
            imageView.setImageResource(R.mipmap.ic_noimage);
            progress_image.setVisibility(View.INVISIBLE);
        }
    }


    public void onback(View view) {
        finish();
    }

    private static String TAG = "Ilham Dev";
    int count;

    private void getCountEventLoad() {

        final DocumentReference doc = db.collection("RankEvents")
                .document(v_idEvents);
        Log.d(TAG, "idEvents    " + v_idEvents);
        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        count = document.getLong("count").intValue();
                        Log.d(TAG, "AAAAAAAAAAAAAAAA    " + count);

                        int hasil = count + 1;
                        Log.d(TAG, "hasil " + hasil);

                        ModelCount modelCount = new ModelCount(hasil);
                        db.collection("RankEvents").document(v_idEvents).set(modelCount).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });


                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


    }
}

class DetailData {

    protected String v_idEvents, v_judul, v_detail, v_waktu, v_tanggal,
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
