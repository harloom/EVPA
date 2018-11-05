package com.example.hx_loom.evpa;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;

import com.example.hx_loom.evpa.Adapater.ListHomeAdapter;
import com.example.hx_loom.evpa.Model.EventLampung;

import java.util.ArrayList;

public class FragmentHome extends Fragment {
    private RecyclerView recyclerView;
    private ListHomeAdapter listHomeAdapter;
    private ArrayList<EventLampung> eventLampungArrayList;


    @Override
    public View onCreateView(LayoutInflater  inflater , ViewGroup container, Bundle savedInstanceState) {

        View roView = inflater.inflate(R.layout.activity_home_fragment, container,false);

        addData();


        recyclerView = (RecyclerView) roView.findViewById(R.id.recycler_view);
        listHomeAdapter = new ListHomeAdapter(this,eventLampungArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listHomeAdapter);


        return  roView;


    }

     void addData(){

         eventLampungArrayList = new ArrayList<>();
         eventLampungArrayList.add(new EventLampung("E001", "UKMExpo", "2018/11/12","08.00" ,"Univ Teknokrat",
                 "-5.382339, 105.257799", "Promosi Unit Kegiatan Mahasiswa" , 13));
         eventLampungArrayList.add(new EventLampung("E002", "Lampung Fair ", "2018/12/15","15.00" ,"PKOR WayHalim",
                 "-5.378548, 105.279429", "Pameran Usaha Kecil dan Menengah " , 16));

     }


}
