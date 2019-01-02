package com.example.hx_loom.evpa.Profile.DetailMyEvents;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.hx_loom.evpa.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class UtilsMyEvents extends BottomSheetDialogFragment {
    View bottomView;
    protected String idEvent, namaEvent, descEvent, tanggalEvent, jamEvent, alamatEvent, urlImage;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    Bundle mArgs;
    AlertDialog.Builder builder;
    StorageReference storageRef = storage.getReference();
    StorageReference desertRef;
    ArrayList<String> listImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*firebasefirestore setting*/
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        /*     */

//        setContentView(R.layout.activity_utils_my_events);
        mArgs = getArguments();
        idEvent = mArgs.getString("idEvent");
        namaEvent = mArgs.getString("namaEvent");
        descEvent = mArgs.getString("descEvent");
        tanggalEvent = mArgs.getString("tanggalEvent");
        jamEvent = mArgs.getString("jamEvent");
        alamatEvent = mArgs.getString("alamatEvent");
        urlImage = mArgs.getString("imgEvent");
        listImage = mArgs.getStringArrayList("urlImage");
        Log.d("HHAHHAHHDAHDHADH ==== ", String.valueOf(listImage.size()));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder = new AlertDialog.Builder(getActivity());
        bottomView = inflater.inflate(R.layout.activity_utils_my_events, container, false);
        final TextView nama = bottomView.findViewById(R.id.util_myEvents_judul);
        final TextView desc = bottomView.findViewById(R.id.util_myEvents_desc);
        final TextView jam = bottomView.findViewById(R.id.util_myEvents_waktu);
        final TextView tanggal = bottomView.findViewById(R.id.util_myEvents_tanggal);
        final TextView tempat = bottomView.findViewById(R.id.util_myEvents_tempat);
        final int sizeWidth =  bottomView.findViewById(R.id.util_myEvents_image).getWidth();
        final int sizeHigth =  bottomView.findViewById(R.id.util_myEvents_image).getMinimumHeight() ;
        try {
            StorageReference imageEvents = storageReference.child("Events");
            if (!urlImage.isEmpty()) {
                StorageReference idEvents = imageEvents.child(idEvent + "/" + urlImage);
                idEvents.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get()
                                .load(uri)
                                .resize(sizeWidth ,sizeHigth )
                                .centerInside()
                                .placeholder(R.color.colorSilver)
                                .into((ImageView) bottomView.findViewById(R.id.util_myEvents_image));
//                    progress_image.setVisibility(View.INVISIBLE);
                        try{
                            nama.setBackgroundColor(getResources().getColor(R.color.white));
                            desc.setBackgroundColor(getResources().getColor(R.color.white));
                            jam.setBackgroundColor(getResources().getColor(R.color.white));
                            tanggal.setBackgroundColor(getResources().getColor(R.color.white));
                            tempat.setBackgroundColor(getResources().getColor(R.color.white));
                        }catch (Exception e){
                            toastMessage(e.toString());
                        }



                        nama.setText(namaEvent);
                        desc.setText(descEvent);
                        jam.setText(jamEvent);
                        tanggal.setText(tanggalEvent);
                        tempat.setText(alamatEvent);


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //erroro
                    }
                });
            } else {
                ImageView imageView = bottomView.findViewById(R.id.imageDetail);
//            Drawable drawable =
//            imageView.setImageResource(getResources().getDrawable());
//            progress_image.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {
            toastMessage("Terjadi Kesalahan");
        }


        bottomView.findViewById(R.id.util_myEvents_buttonHapus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEvents();
            }
        });


        bottomView.findViewById(R.id.util_myEvents_buttonEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMessage("Masih Dalam Pengembangan");
            }
        });

        return bottomView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, (int) (metrics.heightPixels * 0.30));// here i have fragment height 30% of window's height you can set it as per your requirement
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    protected  int a;
    private static String TAG = "Utils Evetns";
    Boolean delFlag = false;
    private void deleteEvents() {

        builder.setTitle("Confirm Hapus").setMessage("Ingin Menghapus Event Ini!").setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.collection("Events").document(idEvent)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                desertRef = storageRef.child("Events");
                               a = 1;
                                for( int i = 0 ; i<listImage.size();i++){
                                    StorageReference spaceRef = desertRef.child(idEvent+"/"+listImage.get(i));
                                    spaceRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {

                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            if(a == listImage.size())
                                            toastMessage("Events Berhasil Di hapus");
                                            dismiss();
                                        }
                                    });
                                    a++;
                                }

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                            }
                        });
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void toastMessage(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }

}
