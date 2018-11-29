package com.example.hx_loom.evpa;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import com.example.hx_loom.evpa.Adapater.ListHomeAdapter;
import com.example.hx_loom.evpa.Model.EventLampung;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import static android.support.constraint.Constraints.TAG;

public class FragmentHome extends Fragment {
    private RecyclerView recyclerView;
    private ListHomeAdapter listHomeAdapter;
    private ArrayList<EventLampung> eventLampungArrayList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    LinearLayoutManager layoutManager;

    private View roView;
    /*  Pagnitaion */
    DocumentSnapshot lastVisible;
    protected int mPostsPerPage = 5;
    protected boolean isScrolling;
    protected boolean isLastItem;
    ProgressBar loading_events,loading_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        roView = inflater.inflate(R.layout.activity_home_fragment, container, false);
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);


        return roView;


    }

    private void addData() {

        final Query first;
        first = db.collection("Events").limit(mPostsPerPage);
        first.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            if (doc.getId() != null) {
                                ArrayList<String> groupImg = (ArrayList<String>) doc.get("imgUrl");
                                Log.d("Data FireStore", doc.getId() + " => " + doc.getData());
                                eventLampungArrayList.add(new EventLampung(doc.getId(), doc.getString("idUsers"), doc.getString("namaEvent"),
                                        doc.getString("desEvent"), doc.getString("namaLokasi"),
                                        doc.getGeoPoint("lokasiGps"), doc.getString("date"), doc.getString("time"),
                                        groupImg));

                            }
                        }
                        listHomeAdapter.notifyDataSetChanged();
                        loading_events.setVisibility(View.GONE);
                        lastVisible = queryDocumentSnapshots.getDocuments()
                                .get(queryDocumentSnapshots.size() - 1);


                        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                super.onScrollStateChanged(recyclerView, newState);

                                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                                    isScrolling = true;
                                }
                            }

                            @Override
                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                super.onScrolled(recyclerView, dx, dy);

                                int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                                int visibleItemCount = layoutManager.getChildCount();
                                int totalItemCount = layoutManager.getItemCount();

                                if (isScrolling && (firstVisibleItem + visibleItemCount == totalItemCount) && !isLastItem) {
                                    isScrolling = false;
                                    Query nextQuery = db.collection("Events")
                                            .startAfter(lastVisible)
                                            .limit(mPostsPerPage);

                                    loading_list.
                                            setVisibility(View.VISIBLE);
                                    nextQuery.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                                if (doc.getId() != null) {
                                                    ArrayList<String> groupImg = (ArrayList<String>) doc.get("imgUrl");
                                                    Log.d("Data FireStore", doc.getId() + " => " + doc.getData());
                                                    eventLampungArrayList.add(new EventLampung(doc.getId(), doc.getString("idUsers"), doc.getString("namaEvent"),
                                                            doc.getString("desEvent"), doc.getString("namaLokasi"),
                                                            doc.getGeoPoint("lokasiGps"), doc.getString("date"), doc.getString("time"),
                                                            groupImg));

                                                }
                                            }
                                            loading_list.setVisibility(View.GONE);
                                            listHomeAdapter.notifyDataSetChanged();
                                            try{
                                                lastVisible = queryDocumentSnapshots.getDocuments()
                                                        .get(queryDocumentSnapshots.size() - 1);
                                            }catch (Exception e){

                                            }

                                            if (queryDocumentSnapshots.size() < mPostsPerPage) {
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

    @Override
    public void onViewCreated(@NonNull View view, @android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loading_events = (ProgressBar) roView.findViewById(R.id.loading_events);
        loading_list = (ProgressBar) roView.findViewById(R.id.loading_eventsList);
        eventLampungArrayList = new ArrayList<>();
        addData();
        recyclerView = (RecyclerView) roView.findViewById(R.id.recycler_view);
        listHomeAdapter = new ListHomeAdapter(this, eventLampungArrayList);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listHomeAdapter);


    }
}


