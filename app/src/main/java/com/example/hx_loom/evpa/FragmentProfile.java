package com.example.hx_loom.evpa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import javax.annotation.Nullable;

public class FragmentProfile extends Fragment {
    private FirebaseAuth mAuth;
    private String uid;
    ImageView image_p;
    TextView nama_p;
    ProgressBar loading_imageprofile;
    protected String data_image;
    private final String TAG = "Database";
    Boolean stateLoad = false;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();


    @Override
    public void onViewCreated(@NonNull View view, @android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();

        view.findViewById(R.id.toHiburan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MusicHiburanActivity.class));
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View profileF = inflater.inflate(R.layout.activity_profile_fragment, container, false);
        image_p = profileF.findViewById(R.id.profile_image);
        nama_p = (TextView) profileF.findViewById(R.id.profile_nama);
        loading_imageprofile = (ProgressBar) profileF.findViewById(R.id.loading_imageprofile);
        loading_imageprofile.getIndeterminateDrawable().setColorFilter(profileF.getResources().getColor(R.color.colorSilver), PorterDuff.Mode.SRC_IN);


        final LinearLayout v_goBack = (LinearLayout) profileF.findViewById(R.id.backfromAbout);


        v_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gg = new Intent(v_goBack.getContext(), AboutActivity.class);
                startActivity(gg);

            }
        });

        final Button v_logout = (Button) profileF.findViewById(R.id.btn_logout);
        v_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = getActivity().getIntent();
                getActivity().finish();
                startActivity(intent);
            }
        });

        return profileF;
    }

    private String tmpurlImage = "Test";

    private void setProfile(String _nama, Uri _urlImage) {
        RequestOptions options = new RequestOptions();
        options.circleCrop();
        options.diskCacheStrategy(DiskCacheStrategy.ALL);

        if (_urlImage.toString().endsWith("gif")) {
            if (getActivity() != null) {

                try {
                    loading_imageprofile.setVisibility(View.INVISIBLE);
                    Glide.with(FragmentProfile.this).asGif()
                            .load(_urlImage).apply(options).into(image_p);
                } catch (Exception e) {

                }

            }
        } else {
            if (getActivity() != null)

                try {
                    loading_imageprofile.setVisibility(View.INVISIBLE);
                    Glide.with(FragmentProfile.this)
                            .load(_urlImage).apply(options).into(image_p);
                } catch (Exception e) {

                }

        }


        nama_p.setText(_nama);


    }

    private void loadData() {
        loading_imageprofile.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            return;
        }
        uid = currentUser.getUid();

        final DocumentReference docUsers = db.collection("Users").document(uid);
        docUsers.addSnapshotListener(new EventListener<DocumentSnapshot>() {

            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("DataBase", "Listen failed.", e);
                    return;
                }
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    Log.d(TAG, "Current data: " + documentSnapshot.getData());
                    final String dataNama = (String) documentSnapshot.getString("nama");
                    data_image = (String) documentSnapshot.getString("image_url");
//                        Log.d(TAG, "user data: " + dataNama);
//                        Log.d(TAG, "url_image: " + data_image);
                    StorageReference users = storageReference.child("Users");
                    StorageReference imageUsrs = users.child(uid + "/" + data_image);
                    imageUsrs.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            setProfile(dataNama, uri);
                        }
                    });

//


                } else {
                    Log.d(TAG, "Current data: null");
//                        stateLoad=false;
                }
            }
        });
//        }

    }


}
