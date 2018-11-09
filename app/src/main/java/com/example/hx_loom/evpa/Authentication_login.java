package com.example.hx_loom.evpa;

import android.inputmethodservice.Keyboard;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Authentication_login extends BottomSheetDialogFragment {


    EditText txt_username,txt_password;
    public Authentication_login() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View authentiactionLogin = inflater.inflate(R.layout.fragment_authentication_login, container, false);
        txt_username = authentiactionLogin.findViewById(R.id.form_username);
        txt_password = authentiactionLogin.findViewById(R.id.form_password);
        return authentiactionLogin;
    }


}
