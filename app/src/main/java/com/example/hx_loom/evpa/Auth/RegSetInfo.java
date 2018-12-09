package com.example.hx_loom.evpa.Auth;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.hx_loom.evpa.MainActivity;
import com.example.hx_loom.evpa.Model.Users;
import com.example.hx_loom.evpa.PostEventFormActivity;
import com.example.hx_loom.evpa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;


public class RegSetInfo extends AppCompatActivity {
    protected String i_email, i_password, i_vPassword;
    private EditText txt_name;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private ImageView buttonImage;
    protected File imageFile;
    protected String imageName;
    protected Boolean isLoadImagae;
    Intent homeAcitivity;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference ref = storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_set_info);
        mAuth = FirebaseAuth.getInstance();
        txt_name = (EditText) findViewById(R.id.info_setname);
        buttonImage = (ImageView) findViewById(R.id.image_setInfo);
        easyImageSetting();
        getPremmisionFolder();
        homeAcitivity = new Intent(this, MainActivity.class);

        txt_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    findViewById(R.id.button_setInfo).setVisibility(View.INVISIBLE);
                } else {
                    findViewById(R.id.button_setInfo).setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        findViewById(R.id.button_setInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.loading_setinfo).setVisibility(View.VISIBLE);
                getIcommingIntent();

            }
        });

        buttonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyImage.openChooserWithGallery(RegSetInfo.this, "Camera/Gallery", 0);
            }
        });

    }

    private void getIcommingIntent() {
        Intent intent = getIntent();
        i_email = intent.getStringExtra("email");
        i_password = intent.getStringExtra("password");
        i_vPassword = intent.getStringExtra("v_password");

        if (i_password.equals(i_vPassword)) {
            mAuth.createUserWithEmailAndPassword(i_email, i_password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                toastMessage("succes register");
                                setDatabaseInformation(user.getUid());
                            }
                        }
                    });
        }

    }

    private void setDatabaseInformation(final String s) {
        String uuid = s;
        final Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime());

        Users users = new Users(i_email, imageName, txt_name.getText().toString(), timestamp);
        Log.d("ViewDataUsers", users + "");
        db.collection("Users").document(uuid).set(users).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Uri uri = Uri.fromFile(imageFile);
                    StorageReference folderUser = ref.child("Users");
                    StorageReference imageUser = folderUser.child(s + "/" + uri.getLastPathSegment());
                    UploadTask uploadTask = imageUser.putFile(uri);
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            findViewById(R.id.loading_setinfo).setVisibility(View.GONE
                            );
                            startActivity(homeAcitivity);
                            finish();
                        }
                    });
                }

            }
        });

    }


    private void easyImageSetting() {

        EasyImage.configuration(this)
                .setImagesFolderName("ImageEvpa")
                .setCopyTakenPhotosToPublicGalleryAppFolder(false)
                .setCopyPickedImagesToPublicGalleryAppFolder(false)
                .setAllowMultiplePickInGallery(false);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {

            @Override
            public void onImagesPicked(@NonNull List<File> list, EasyImage.ImageSource imageSource, int i) {

                imageFile = list.get(0);
                String path = String.valueOf(list);
                String filename = path.substring(path.lastIndexOf("/") + 1);
                String fix = filename.substring(0, filename.lastIndexOf(']'));
                imageName = fix;

                Glide.with(RegSetInfo.this)
                        .load(imageFile)
                        .into(buttonImage);

                isLoadImagae = true;
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                // Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA_IMAGE) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(RegSetInfo.this);
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

    private void toastMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

}
