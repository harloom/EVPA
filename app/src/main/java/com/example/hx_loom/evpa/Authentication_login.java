package com.example.hx_loom.evpa;

import android.inputmethodservice.Keyboard;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class Authentication_login extends BottomSheetDialogFragment {

    public Authentication_login() {
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View authentiactionLogin =  inflater.inflate(R.layout.fragment_authentication_login, container, false);
        return  authentiactionLogin ;
    }


}
