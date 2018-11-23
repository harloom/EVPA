package com.example.hx_loom.evpa;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hx_loom.evpa.Adapater.ImagePostAdapter;
import com.example.hx_loom.evpa.Adapater.MapAdpater;
import com.example.hx_loom.evpa.Model.MapModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.net.IDN;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

public class PostEventFormActivity extends AppCompatActivity {
    //firebase
    FirebaseFirestore dbMap = FirebaseFirestore.getInstance();

    //deklrasi custom
    private Calendar myCalendar;
    private static final String PHOTO_KEYS = "P_Evpa";
    private RecyclerView recyclerView;
    private View buttonPicture,
            buttonCalender,
            buttonTime;
    private Spinner spinnerMap;
    private DatePickerDialog.OnDateSetListener date;
    private TimePickerDialog timePickerDialog;
    private TextView txt_cal;
    private ImagePostAdapter imagePostAdapter;
    private ArrayList<File> photos = new ArrayList<>();
    private ArrayList<MapModel> mapModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*firebasefirestore setting*/
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        dbMap.setFirestoreSettings(settings);
        /*     */
        setContentView(R.layout.activity_post_event_form);
        myCalendar = Calendar.getInstance();
        getCalender();
        recyclerView = findViewById(R.id.recycler_viewImage);
        buttonPicture = findViewById(R.id.btn_pitcurePost);
        buttonCalender = findViewById(R.id.btn_cal);
        buttonTime = findViewById(R.id.btn_time);
        txt_cal = findViewById(R.id.text_cal);
        spinnerMap = (Spinner) findViewById(R.id.post_spinner);
        addMap();




        if (savedInstanceState != null) {
            photos = (ArrayList<File>) savedInstanceState.getSerializable(PHOTO_KEYS);
        }
        imagePostAdapter = new ImagePostAdapter(this, photos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(imagePostAdapter);

        /* function getPremmisionFolder */
        getPremmisionFolder();

        /*function setSettingLibrary*/
        easyImageSetting();

        /*fucntion cekgalaeryAvailbale*/
        checkGalleryAppAvailability();

        buttonPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyImage.openChooserWithGallery(PostEventFormActivity.this, "Camera/Gallery Senpai", 0);
            }
        });

        buttonCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(PostEventFormActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        buttonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime();
            }

        });


    }

    private void addMap() {
        mapModels = new ArrayList<>();
        dbMap.collection("Map").orderBy("namaLokasi",Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d("MAP DATABASE", document.getId() + " => " + document.getData());

                                mapModels.add(new MapModel(document.getId(), document.getString("namaLokasi"),
                                        document.getGeoPoint("Gps")));

                            }
                                MapAdpater mapAdpater = new MapAdpater(getApplicationContext(), mapModels);
                                spinnerMap.setAdapter(mapAdpater);

                                spinnerMap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        Log.d("MAP DATABASE", mapModels.get(position).getNameLokasi());
                                        Toast.makeText(getApplicationContext(), mapModels.get(position).getGps().toString(), Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                        } else {
//                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try {
            Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
        } catch (Exception e) {

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagesPicked(@NonNull List<File> list, EasyImage.ImageSource imageSource, int i) {
                onPhotosRetruning(list);
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                // Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA_IMAGE) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(PostEventFormActivity.this);
                    if (photoFile != null) photoFile.delete();
                }
            }

            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                super.onImagePickerError(e, source, type);
                toastMessage("Something Error");
            }
        });
    }

    private void onPhotosRetruning(List<File> __photos) {
        /* add photos Array */
        recyclerView.setVisibility(View.VISIBLE);
        photos.addAll(__photos);
        Log.d("Array", "Array Photos : " + photos);
        imagePostAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(photos.size() - 1);
    }

    public void backMenu(View view) {
        finish();
    }

    @Override
    public boolean isDestroyed() {
        return super.isDestroyed();
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putSerializable(PHOTO_KEYS, photos);
    }


    private void getPremmisionFolder() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Nammu.askForPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionCallback() {
                @Override
                public void permissionGranted() {
                    //Nothing, this sample saves to Public gallery so it needs permission
                }

                @Override
                public void permissionRefused() {
                    finish();
                }
            });
        }

    }

    private void easyImageSetting() {
        EasyImage.configuration(this)
                .setImagesFolderName("ImageEvpa")
                .setCopyTakenPhotosToPublicGalleryAppFolder(false)
                .setCopyPickedImagesToPublicGalleryAppFolder(false)
                .setAllowMultiplePickInGallery(true);
    }

    private void checkGalleryAppAvailability() {
        if (!EasyImage.canDeviceHandleGallery(this)) {
            //Device has no app that handles gallery intent
            buttonPicture.setVisibility(View.GONE);
        }
    }

    private void toastMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void getCalender() {
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDate();
            }

        };
    }

    private void getTime() {
        timePickerDialog = new TimePickerDialog(PostEventFormActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                updateLabelTime(hourOfDay + "." + minute);
            }
        }, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true);

        timePickerDialog.show();

    }

    private void updateLabelDate() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        txt_cal.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabelTime(String s) {
        TextView v_time = findViewById(R.id.txt_time);
        v_time.setText(s + " " + TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT));
    }


}


