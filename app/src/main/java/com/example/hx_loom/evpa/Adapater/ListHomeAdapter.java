package com.example.hx_loom.evpa.Adapater;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hx_loom.evpa.DetailActivity;
import com.example.hx_loom.evpa.FragmentHome;
import com.example.hx_loom.evpa.Model.EventLampung;
import com.example.hx_loom.evpa.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListHomeAdapter extends RecyclerView.Adapter<ListHomeAdapter.EventLampungViewHolder> {

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();

    private ArrayList<EventLampung> dataList;
    Context mcontext;

    public ListHomeAdapter(FragmentHome context, ArrayList<EventLampung> dataList) {
        this.dataList = dataList;
        mcontext = context.getActivity();
    }


    @NonNull
    @Override
    public EventLampungViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_home, parent, false);
        return new EventLampungViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EventLampungViewHolder holder, final int position) {
        holder.txt_NamaEvent.setText(dataList.get(position).getNamaEvent());
        holder.txt_DetailEvent.setText(dataList.get(position).getDesEvent());
        holder.txt_TanggalEvent.setText(dataList.get(position).getDate());
        holder.txt_NamaLokasiEvent.setText(dataList.get(position).getNamaLokasi());
        holder.txt_JamEvent.setText(dataList.get(position).getTime());

        /* download gambar*/
        StorageReference imageEvents = storageReference.child("Events");
        StorageReference idEvents = imageEvents.child(dataList.get(position).getIdEvents()+"/"+dataList.get(position).getImgUrl().get(0));
        idEvents.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get()
                        .load(uri)
                        .fit()
                        .centerCrop()
                        .into(holder.imgE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //erroro
            }
        });



        holder.parent_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, DetailActivity.class);
                intent.putExtra("judul", dataList.get(position).getNamaEvent());
                intent.putExtra("detail_event", dataList.get(position).getDesEvent());
                intent.putExtra("detail_waktu", dataList.get(position).getTime());
                intent.putExtra("detail_tanggal", dataList.get(position).getDate());
                intent.putExtra("detail_namaLokasi", dataList.get(position).getNamaLokasi());
                intent.putExtra("idEvent",dataList.get(position).getIdEvents());
                intent.putStringArrayListExtra("arrayImage",dataList.get(position).getImgUrl());
                mcontext.startActivity(intent);
            }

        });

    }

    @Override
    public int getItemCount() {

        return (dataList != null) ? dataList.size() : 0;
    }

    public String getLastItemid() {
        return dataList.get(dataList.size() - 1).getIdEvents();
    }

    public class EventLampungViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_NamaEvent,
                txt_TanggalEvent,
                txt_NamaLokasiEvent,
                txt_DetailEvent,
                txt_JamEvent;
        private ImageView imgE;
        private LinearLayout parent_home;

        public EventLampungViewHolder(View itemView) {
            super(itemView);
            txt_NamaEvent = (TextView) itemView.findViewById(R.id.txtJudulEvent);
            txt_DetailEvent = (TextView) itemView.findViewById(R.id.txtDetailEvent);
            txt_TanggalEvent = (TextView) itemView.findViewById(R.id.txtTanggalEvent);
            txt_NamaLokasiEvent = (TextView) itemView.findViewById(R.id.txtNamaLokasiEvent);
            txt_JamEvent = (TextView) itemView.findViewById(R.id.txtJamEvent);
            parent_home = (LinearLayout) itemView.findViewById(R.id.parent_home);
            imgE = (ImageView) itemView.findViewById(R.id.image_event);

        }
    }
}



