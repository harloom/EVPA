package com.example.hx_loom.evpa.Profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;

import com.example.hx_loom.evpa.Adapater.ListProfileAdapter;
import com.example.hx_loom.evpa.Model.EventLampung;
import com.example.hx_loom.evpa.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProfileDataList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListProfileAdapter listProfileAdapter;
    private ArrayList<EventLampung> arrayList;
    private Integer mPostsPerPage = 5;
    LinearLayoutManager layoutManager;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    String keyUser;

    //pagintation
    DocumentSnapshot lastVisible;
    protected boolean isScrolling;
    protected boolean isLastItem;
    protected  boolean nextQueryComplate ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_data);
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);


        arrayList = new ArrayList<>();
        _i_loadData();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_dataProfileList);
        listProfileAdapter = new ListProfileAdapter(this, arrayList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }

    private void _i_loadData() {
        Intent intent = getIntent();
        keyUser = intent.getStringExtra("key");
        final Query _first = db.collection("Events").whereEqualTo("idUsers", keyUser)
                .orderBy("timestamp", Query.Direction.DESCENDING).limit(mPostsPerPage);
        _first.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                nextQueryComplate = true;
                for (QueryDocumentSnapshot data : queryDocumentSnapshots){
                        if (!(data.getId().isEmpty())){
                            ArrayList<String> groupImg = (ArrayList<String>) data.get("imgUrl");
                            arrayList.add(new EventLampung(data.getId(), data.getString("idUsers"), data.getString("namaEvent"),
                                    data.getString("desEvent"), data.getString("namaLokasi"),
                                    data.getGeoPoint("lokasiGps"), data.getString("date"), data.getString("time"),
                                    groupImg));
                        }
                }
                recyclerView.setAdapter(listProfileAdapter);
                lastVisible = queryDocumentSnapshots.getDocuments()
                        .get(queryDocumentSnapshots.size() - 1);

                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL && nextQueryComplate) {
                            isScrolling = true;
                            nextQueryComplate = false;
                        }
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                        int visibleItemCount = layoutManager.getChildCount();
                        int totalItemCount = layoutManager.getItemCount();

                        if (isScrolling && (firstVisibleItem + visibleItemCount == totalItemCount) && !isLastItem ) {
                            isScrolling = false;
                            Query nextQuery = db.collection("Events").whereEqualTo("idUsers", keyUser)
                                    .orderBy("timestamp",Query.Direction.DESCENDING)
                                    .startAfter(lastVisible)
                                    .limit(mPostsPerPage);

                            nextQuery.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                        if (doc.getId() != null) {
                                            ArrayList<String> groupImg = (ArrayList<String>) doc.get("imgUrl");
//                                                    Log.d("Data FireStore", doc.getId() + " => " + doc.getData());
                                            arrayList.add(new EventLampung(doc.getId(), doc.getString("idUsers"), doc.getString("namaEvent"),
                                                    doc.getString("desEvent"), doc.getString("namaLokasi"),
                                                    doc.getGeoPoint("lokasiGps"), doc.getString("date"), doc.getString("time"),
                                                    groupImg));

                                        }
                                    }
                                    nextQueryComplate = true;
                                    if(queryDocumentSnapshots.size() != 0){
                                        lastVisible = queryDocumentSnapshots.getDocuments()
                                                .get(queryDocumentSnapshots.size() - 1);
                                    }


                                    listProfileAdapter.notifyDataSetChanged();

                                    if (queryDocumentSnapshots.size() < mPostsPerPage ) {
                                        isLastItem  = true;
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });

    }


}
