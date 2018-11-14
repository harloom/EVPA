package com.example.hx_loom.evpa.Adapater;


/*
        create by Harloom November 2018

 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hx_loom.evpa.PostEventFormActivity;
import com.example.hx_loom.evpa.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImagePostAdapter  extends RecyclerView.Adapter<ImagePostAdapter.ViewHolder> {
    private Context context;
    private List<File> imagefiles;

    public ImagePostAdapter(Context context, ArrayList<File> photos) {
        this.context =  context;
        this.imagefiles = photos;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.image_view_list_post,parent,false);
        return  new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get()
                .load(imagefiles.get(position))
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return (imagefiles != null) ? imagefiles.size() : 0;
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_viewPost);

        }
    }
}
