package com.example.hx_loom.evpa.Adapater;



import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hx_loom.evpa.Model.EventLampung;
import com.example.hx_loom.evpa.R;

import java.util.ArrayList;

public class ListHomeAdapter extends RecyclerView.Adapter<ListHomeAdapter.EventLampungViewHolder> {


        private ArrayList<EventLampung> dataList;

        public ListHomeAdapter(ArrayList<EventLampung> dataList){
            this.dataList = dataList;

        }



    @NonNull
    @Override
    public EventLampungViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_home,parent,false);
        return  new EventLampungViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventLampungViewHolder holder, int position) {
        holder.txt_NamaEvent.setText(dataList.get(position).getNamaEvent());
        holder.txt_DetailEvent.setText(dataList.get(position).getDetailEvent());
        holder.txt_TanggalEvent.setText(dataList.get(position).getTanggalEvent());
        holder.txt_NamaLokasiEvent.setText(dataList.get(position).getNamaLokasi());
        holder.txt_JamEvent.setText(dataList.get(position).getJamEvent());
    }

    @Override
    public int getItemCount() {

        return (dataList != null) ? dataList.size() : 0;
    }

    public class EventLampungViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_NamaEvent,
                txt_TanggalEvent,
                txt_NamaLokasiEvent,
                txt_DetailEvent,
                txt_JamEvent;

        public EventLampungViewHolder(View itemView) {
            super(itemView);
            txt_NamaEvent = (TextView) itemView.findViewById(R.id.txtJudulEvent);
            txt_DetailEvent = (TextView) itemView.findViewById(R.id.txtDetailEvent);
            txt_TanggalEvent = (TextView) itemView.findViewById(R.id.txtTanggalEvent);
            txt_NamaLokasiEvent = (TextView) itemView.findViewById(R.id.txtNamaLokasiEvent);
            txt_JamEvent = (TextView) itemView.findViewById(R.id.txtJamEvent);

        }
    }
}



