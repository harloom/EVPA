package com.example.hx_loom.evpa;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;

import com.example.hx_loom.evpa.Adapater.ListRankAdapter;
import com.example.hx_loom.evpa.Model.EventLampung;
import com.example.hx_loom.evpa.Model.RankModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class FragmentRank extends Fragment {
    private RecyclerView recyclerView;
    private ListRankAdapter mAdapter;
    ArrayList<RankModel> listRank;


    /* Firebase db*/
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        View viewRank = inflater.inflate(R.layout.activity_rank_fragment, container, false);
        listRank = new ArrayList<>();
        recyclerView = viewRank.findViewById(R.id.recycler_view_rank);
        mAdapter = new ListRankAdapter(this, listRank);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(viewRank.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        loadDataFirebase();
        mAdapter.notifyDataSetChanged();
        return viewRank;
    }


    private void loadDataFirebase() {

        db.collection("RankEvents").orderBy("count", Query.Direction.DESCENDING).limit(5)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (final QueryDocumentSnapshot a : task.getResult()) {
                                Log.d(TAG, a.getId() + " => " + a.getData());
                                String dd = a.getId();


                                DocumentReference dataShort = db.collection("Events").document(dd);
                                dataShort.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                    @Override
                                    public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                                        RankModel rankModel = new RankModel(documentSnapshot.getString("namaEvent"), a.getLong("count").intValue(), documentSnapshot.getId());
                                        listRank.add(rankModel);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                });
                            }

//                            RankModel rankModel = new RankModel(doc.getString("namaEvent"), a.getLong("count").intValue(), doc.getId());
//                            listRank.add(rankModel);
//                            mAdapter.notifyDataSetChanged();
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }


}
