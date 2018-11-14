package com.example.hx_loom.evpa;

import android.Manifest;
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
import android.view.View;
import android.widget.Toast;

import com.example.hx_loom.evpa.Adapater.ImagePostAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

public class PostEventFormActivity extends AppCompatActivity {

    private static final String PHOTO_KEYS = "P_Evpa";
    protected RecyclerView recyclerView;
    protected View buttonPicture;
    private ImagePostAdapter imagePostAdapter;
    private ArrayList<File> photos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_event_form);

        recyclerView = findViewById(R.id.recycler_viewImage);
        buttonPicture = findViewById(R.id.btn_pitcurePost);
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
                EasyImage.openChooserWithGallery(PostEventFormActivity.this,  "Camera/Gallery Senpai",  0);
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try {
            Nammu.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }catch (Exception e){

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

    private void onPhotosRetruning(List<File> __photos){
        /* add photos Array */
        photos.addAll(__photos);
        Log.d("Array","Array Photos : "+photos);
        imagePostAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(photos.size() -1 );
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
        outState.putSerializable(PHOTO_KEYS,photos);
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
}


