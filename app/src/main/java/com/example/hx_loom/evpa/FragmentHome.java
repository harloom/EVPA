package com.example.hx_loom.evpa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;

import com.example.hx_loom.evpa.Adapater.MahasiswaAdapter;
import com.example.hx_loom.evpa.Model.Mahasiswa;

import java.util.ArrayList;

public class FragmentHome extends Fragment {
    private RecyclerView recyclerView;
    private MahasiswaAdapter mahasiswaAdapter;
    private ArrayList<Mahasiswa> mahasiswaArrayList;


    @Override
    public View onCreateView(LayoutInflater  inflater , ViewGroup container, Bundle savedInstanceState) {
//            return inflater.inflate(R.layout.activity_home_fragment, container, false);
        View roView = inflater.inflate(R.layout.activity_home_fragment, container,false);

        addData();


        recyclerView = (RecyclerView) roView.findViewById(R.id.recycler_view);
        mahasiswaAdapter = new MahasiswaAdapter(mahasiswaArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mahasiswaAdapter);


        return  roView;


    }

     void addData(){

         mahasiswaArrayList = new ArrayList<>();
         mahasiswaArrayList.add(new Mahasiswa("Dimas Maulana", "1414370309", "123456789"));
         mahasiswaArrayList.add(new Mahasiswa("Fadly Yonk", "1214234560", "987654321"));
         mahasiswaArrayList.add(new Mahasiswa("Ariyandi Nugraha", "1214230345", "987648765"));
         mahasiswaArrayList.add(new Mahasiswa("Aham Siswana", "1214378098", "098758124"));

     }


}
