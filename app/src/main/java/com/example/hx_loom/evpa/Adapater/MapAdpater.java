package com.example.hx_loom.evpa.Adapater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hx_loom.evpa.Model.MapModel;
import com.example.hx_loom.evpa.R;

import java.util.ArrayList;

public class MapAdpater extends BaseAdapter {
    private ArrayList<MapModel> dataList;
    private Context context;
    private LayoutInflater inflter;
    public MapAdpater(Context applicationContext, ArrayList<MapModel> mapModels) {
        this.context = applicationContext;
        this.dataList = mapModels;
        this.inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflter.inflate(R.layout.list_map,null);
        TextView _txtMap = (TextView) convertView.findViewById(R.id.textMapView);
        _txtMap.setText((String) dataList.get(position).getNameLokasi());

        return convertView;
    }
}
