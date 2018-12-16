package com.example.hx_loom.evpa.Profile;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
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

import java.net.URI;

import javax.annotation.Nullable;

public class Profile_Account extends AppCompatActivity {
    private ImageView image_p;
    private TextView name_p;
    private EditText c_password;
    private EditText c_new_passowrd;
    protected EditText c_confirm_newPassowrd;
    private String keyUser;
    ProgressBar loading_image;

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



        /* Call Function*/
        loadInten();
        loadDataFirebase();
        watch_cPassword();


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


}
