package com.example.hx_loom.evpa.Adapater;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hx_loom.evpa.Model.EventLampung;
import com.example.hx_loom.evpa.Profile.DetailMyEvents.UtilsMyEvents;
import com.example.hx_loom.evpa.Profile.ProfileDataList;
import com.example.hx_loom.evpa.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListProfileAdapter extends RecyclerView.Adapter<ListProfileAdapter.ListViewHolder> {
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    private ArrayList<EventLampung> list;
    Context mcontext;
    UtilsMyEvents utilsMyEvents = new UtilsMyEvents();

    public ListProfileAdapter(ProfileDataList __context, ArrayList<EventLampung> __list) {
        this.list = __list;
        this.mcontext = __context;
    }

    @NonNull
    @Override
    public ListProfileAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_arrayprofile, parent, false);

        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListProfileAdapter.ListViewHolder holder, final int position) {
        //* Mengambil beberapa karakter desc + ...* //

        if (list.get(position).getDesEvent().length() > 30) {
            String subDesc = list.get(position).getDesEvent().substring(0, 30) + "..";
            holder._desc.setText(subDesc);
        } else {
            holder._desc.setText(list.get(position).getDesEvent());
        }
        holder._judul.setText(list.get(position).getNamaEvent());
        holder._alamat.setText(list.get(position).getNamaLokasi());
        holder._tanggal.setText(list.get(position).getDate());

        StorageReference imageEvents = storageReference.child("Events");
        if (list.get(position).getImgUrl().size() != 0) {
            StorageReference idEvents = imageEvents.child(list.get(position).getIdEvents() + "/" + list.get(position).getImgUrl().get(0));
            holder._image.setImageResource(0);
            holder._loading_imageList.setVisibility(View.VISIBLE);
            idEvents.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get()
                            .load(uri)
                            .fit()
                            .noFade()
                            .centerCrop()
                            .into(holder._image);
                    holder._loading_imageList.setVisibility(View.INVISIBLE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //erroro
                }
            });
        } else {

            holder._image.setImageResource(R.mipmap.ic_noimage);
            holder._loading_imageList.setVisibility(View.INVISIBLE);
        }


        holder._intentDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("idEvent", list.get(position).getIdEvents());
                args.putString("namaEvent",list.get(position).getNamaEvent());
                args.putString("alamatEvent" , list.get(position).getNamaLokasi());
                args.putString("tanggalEvent",list.get(position).getDate());
                args.putString("jamEvent",list.get(position).getTime());
                args.putString("descEvent",list.get(position).getDesEvent());
                args.putString("imgEvent", String.valueOf(list.get(position).getImgUrl().get(0)));
                args.putStringArrayList("urlImage",list.get(position).getImgUrl());
                BottomSheetDialogFragment utilsMyEvents = new UtilsMyEvents();
                utilsMyEvents.setArguments(args);
                utilsMyEvents.show(((FragmentActivity)mcontext).getSupportFragmentManager(), "Detail Bottom Sheet");

            }
        });
    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        private ImageView _image;
        private TextView _judul, _desc, _tanggal, _alamat;
        private ProgressBar _loading_imageList;
        private LinearLayout _intentDetail;
        private Context context;
        private  FragmentManager fragmentManager;

        public ListViewHolder(View itemView) {
            super(itemView);
            _image = itemView.findViewById(R.id.p_image_event);
            _judul = itemView.findViewById(R.id.p_txtJudulEvent);
            _desc = itemView.findViewById(R.id.p_txtDetailEvent);
            _tanggal = itemView.findViewById(R.id.p_txtTanggalEvent);
            _alamat = itemView.findViewById(R.id.p_txtNamaLokasiEvent);
            _loading_imageList = itemView.findViewById(R.id.loading_imageListMyevents);
            _loading_imageList.getIndeterminateDrawable().setColorFilter(itemView.getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
            _intentDetail = itemView.findViewById(R.id.p_profieList);


        }
    }
}
