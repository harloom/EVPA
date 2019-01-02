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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hx_loom.evpa.DetailActivity;
import com.example.hx_loom.evpa.FragmentHome;
import com.example.hx_loom.evpa.Model.EventLampung;
import com.example.hx_loom.evpa.R;
import com.example.hx_loom.evpa.Report.Report;
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
        //* Mengambil beberapa karakter desc + ...* //
        if (dataList.get(position).getDesEvent().length() > 30) {
            String subDesc = dataList.get(position).getDesEvent().substring(0, 30) + "..";
            holder.txt_DetailEvent.setText(subDesc);
        } else {
            holder.txt_DetailEvent.setText(dataList.get(position).getDesEvent());
        }
        holder.txt_NamaEvent.setText(dataList.get(position).getNamaEvent());
        holder.txt_TanggalEvent.setText(dataList.get(position).getDate());
        holder.txt_NamaLokasiEvent.setText(dataList.get(position).getNamaLokasi());

        if(dataList.get(position).getTime().length() ==3){
            holder.txt_JamEvent.setText(dataList.get(position).getTime()+0);
        }else{
            holder.txt_JamEvent.setText(dataList.get(position).getTime());
        }


        /* download gambar*/
        StorageReference imageEvents = storageReference.child("Events");
        if (dataList.get(position).getImgUrl().size() != 0) {
            StorageReference idEvents = imageEvents.child(dataList.get(position).getIdEvents() + "/" + dataList.get(position).getImgUrl().get(0));
            holder.imgE.setImageResource(0);
            holder.loading_imageList.setVisibility(View.VISIBLE);
            idEvents.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get()
                            .load(uri)
                            .fit()
                            .noFade()
                            .centerCrop()
                            .into(holder.imgE);
                    holder.loading_imageList.setVisibility(View.INVISIBLE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //erroro
                }
            });
        } else {

            holder.imgE.setImageResource(R.mipmap.ic_noimage);
            holder.loading_imageList.setVisibility(View.INVISIBLE);
        }


        holder.parent_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, DetailActivity.class);
                intent.putExtra("judul", dataList.get(position).getNamaEvent());
                intent.putExtra("detail_event", dataList.get(position).getDesEvent());
                intent.putExtra("detail_waktu", dataList.get(position).getTime());
                intent.putExtra("detail_tanggal", dataList.get(position).getDate());
                intent.putExtra("detail_namaLokasi", dataList.get(position).getNamaLokasi());
                intent.putExtra("idEvent", dataList.get(position).getIdEvents());
                intent.putStringArrayListExtra("arrayImage", dataList.get(position).getImgUrl());
                mcontext.startActivity(intent);
            }

        });

        holder.directMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creates an Intent that will load a map of San Francisco
                Double lt = dataList.get(position).getLokasiGps().getLatitude();
                Double ltdude = dataList.get(position).getLokasiGps().getLongitude();
                String urilMap = "google.navigation:q=" + lt + "," + ltdude + "&mode=d";
                Log.d("Map longtitide", urilMap);
                Uri gmmIntentUri = Uri.parse(urilMap);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                mcontext.startActivity(mapIntent);
            }
        });

        holder.report_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext,Report.class);
                intent.putExtra("idEvent",dataList.get(position).getIdEvents());
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
        private LinearLayout parent_home,
                directMap , report_event;
        private ProgressBar loading_imageList;

        public EventLampungViewHolder(View itemView) {
            super(itemView);
            report_event = itemView.findViewById(R.id.report_event);
            txt_NamaEvent = itemView.findViewById(R.id.txtJudulEvent);
            txt_DetailEvent = itemView.findViewById(R.id.txtDetailEvent);
            txt_TanggalEvent = itemView.findViewById(R.id.txtTanggalEvent);
            txt_NamaLokasiEvent = itemView.findViewById(R.id.txtNamaLokasiEvent);
            txt_JamEvent = itemView.findViewById(R.id.txtJamEvent);
            parent_home = itemView.findViewById(R.id.parent_home);
            directMap = itemView.findViewById(R.id.direcMap);
            imgE = itemView.findViewById(R.id.image_event);
            loading_imageList = itemView.findViewById(R.id.loading_imageList);
            loading_imageList.getIndeterminateDrawable().setColorFilter(itemView.getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);

        }
    }
}



