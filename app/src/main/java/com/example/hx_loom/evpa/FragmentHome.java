package com.example.hx_loom.evpa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;

public class FragmentHome extends Fragment {

    @Override
    public View onCreateView(LayoutInflater  inflater , ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.activity_home_fragment, container, false);

    }
}
