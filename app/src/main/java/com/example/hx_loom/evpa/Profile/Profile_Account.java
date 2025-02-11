package com.example.hx_loom.evpa.Profile;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.hx_loom.evpa.Auth.RegSetInfo;
import com.example.hx_loom.evpa.FragmentProfile;
import com.example.hx_loom.evpa.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.net.URI;
import java.util.List;

import javax.annotation.Nullable;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class Profile_Account extends AppCompatActivity {
    private ImageView image_p;
    private TextView name_p;
    private EditText c_password;
    private EditText c_new_passowrd;
    protected EditText c_confirm_newPassowrd;
    private String keyUser;
    ProgressBar loading_image;
    protected File imageFile;
    protected String imageName = "noImage.jpg";

    // firebase storage
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__account);
        image_p = (ImageView) findViewById(R.id.account_image_lyt);
        name_p = (TextView) findViewById(R.id.account_name_lyt);
        c_password = (EditText) findViewById(R.id.account_cPassword_lyt);
        c_new_passowrd = (EditText) findViewById(R.id.account_new_password_lyt);
        c_confirm_newPassowrd = (EditText) findViewById(R.id.accoutn_confirm_password_lyt);
        loading_image = (ProgressBar) findViewById(R.id.accout_loading_image_lyt);
        loading_image.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorSilver), PorterDuff.Mode.SRC_IN);

        findViewById(R.id.account_getPhoto_lyt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyImage.openChooserWithGallery(Profile_Account.this, "Camera/Gallery", 0);
            }
        });

        /* Call Function*/
        loadInten();
        loadDataFirebase();
        watch_cPassword();
        easyImageSetting();


    }

    private void loadDataFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        final DocumentReference docUsers = db.collection("Users").document(keyUser);
        docUsers.addSnapshotListener(new EventListener<DocumentSnapshot>() {

            @Override
            public void onEvent(@Nullable final DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("DataBase", "Listen failed.", e);
                    return;
                }
                if (documentSnapshot != null && documentSnapshot.exists()) {


                    String data_image = documentSnapshot.getString("image_url");
                    final String data_name = documentSnapshot.getString("nama");
//                        Log.d(TAG, "user data: " + dataNama);
//                        Log.d(TAG, "url_image: " + data_image);
                    StorageReference users = storageReference.child("Users");
                    StorageReference imageUsrs = users.child(keyUser + "/" + data_image);
                    imageUsrs.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            loadImage(uri, data_name);
                        }
                    });

//


                } else {
//                    Log.d(TAG, "Current data: null");
//                        stateLoad=false;
                }
            }
        });
    }

    public void backMenu(View view) {
        finish();
    }

    private void loadImage(Uri _urlImage, String _name) {
        RequestOptions options = new RequestOptions();
        options.circleCrop();
        options.placeholder(R.color.colorSilver);
        options.diskCacheStrategy(DiskCacheStrategy.ALL);

        name_p.setText(_name);

        if (_urlImage.toString().endsWith("gif")) {


            try {
                loading_image.setVisibility(View.INVISIBLE);
                Glide.with(this).asGif()
                        .load(_urlImage).apply(options).into(image_p);
            } catch (Exception e) {


            }
        } else {


            try {
                loading_image.setVisibility(View.INVISIBLE);
                Glide.with(this)
                        .load(_urlImage).apply(options).into(image_p);
            } catch (Exception e) {


            }
        }
    }

    private void loadInten() {
        Intent intent = getIntent();
        keyUser = intent.getStringExtra("key");
    }

    private void watch_cPassword() {
        final String[] _newP = new String[1];

        c_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty() || s.toString().length() < 6) {
                    findViewById(R.id.account_btnChange_lyt).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.account_new_password_lyt).setEnabled(true);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        c_new_passowrd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty() || s.toString().length() < 6) {
                    c_confirm_newPassowrd.setText("");
                    findViewById(R.id.account_btnChange_lyt).setVisibility(View.GONE);
                    c_confirm_newPassowrd.setEnabled(false);
                } else {
                    _newP[0] = s.toString();
                    c_confirm_newPassowrd.setEnabled(true);
                    Log.d("_newP", _newP[0] + count);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        c_confirm_newPassowrd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty() || s.toString().length() < 6) {
                    findViewById(R.id.account_btnChange_lyt).setVisibility(View.GONE);

                } else {
                    if (_newP[0].equals(s.toString())) {
                        findViewById(R.id.account_btnChange_lyt).setVisibility(View.VISIBLE);
                    } else {

                        findViewById(R.id.account_btnChange_lyt).setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {

            @Override
            public void onImagesPicked(@NonNull List<File> list, EasyImage.ImageSource imageSource, int i) {
                RequestOptions options = new RequestOptions();
                options.circleCrop();
                options.diskCacheStrategy(DiskCacheStrategy.ALL);
                imageFile = list.get(0);
                String path = String.valueOf(list);
                String filename = path.substring(path.lastIndexOf("/") + 1);
                String fix = filename.substring(0, filename.lastIndexOf(']'));
                imageName = fix;

                Glide.with(Profile_Account.this)
                        .load(imageFile)
                        .apply(options)
                        .into(image_p);


            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                // Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA_IMAGE) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(Profile_Account.this);
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
