package com.example.hx_loom.evpa.Adapater;


import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hx_loom.evpa.DetailActivity;
import com.example.hx_loom.evpa.FragmentHome;
import com.example.hx_loom.evpa.FragmentRank;
import com.example.hx_loom.evpa.Model.EventLampung;
import com.example.hx_loom.evpa.Model.RankModel;
import com.example.hx_loom.evpa.R;
import com.example.hx_loom.evpa.Report.Report;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListRankAdapter extends RecyclerView.Adapter<ListRankAdapter.EventRankViewHolder> {

//    FirebaseStorage storage = FirebaseStorage.getInstance();
//    StorageReference storageReference = storage.getReference();

    private ArrayList<RankModel> dataList;
    Context mcontext;

    public ListRankAdapter(FragmentRank context, ArrayList<RankModel> dataList) {
        this.dataList = dataList;
        mcontext = context.getActivity();
    }


    @NonNull
    @Override
    public EventRankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_rank, parent, false);
        return new EventRankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  EventRankViewHolder holder,  int position) {


        holder.rankNum.setText(position+1+"");
        holder.rankJudulT.setText(dataList.get(position).getNamaEvent());
        holder.rankCountSee.setText(dataList.get(position).getCountSee() +"");

    }

    @Override
    public int getItemCount() {

        return (dataList != null) ? dataList.size() : 0;
    }



    public class EventRankViewHolder extends RecyclerView.ViewHolder {
         TextView rankJudulT,
                 rankCountSee,
                rankNum;


        public EventRankViewHolder(View itemView) {
            super(itemView);
            rankJudulT =(TextView) itemView.findViewById(R.id.rankJudul);
            rankCountSee = (TextView) itemView.findViewById(R.id.rankCount);
            rankNum = (TextView) itemView.findViewById(R.id.no_rank);
        }
    }
}



