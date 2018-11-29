package com.example.hx_loom.evpa;

import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Authentication_login extends BottomSheetDialogFragment {
    private FirebaseAuth mAuth;
    private ProgressBar loading_login;
    EditText txt_username, txt_password;

    public Authentication_login() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loading_login = (ProgressBar) view.findViewById(R.id.loading_login);
        loading_login.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.oneesan),PorterDuff.Mode.SRC_IN);
        final EditText form_username = view.findViewById(R.id.form_username);
        final EditText form_password = view.findViewById(R.id.form_password);
        Button action_loginButton = view.findViewById(R.id.button_login);
        action_loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value_u = form_username.getText().toString();
                String value_p = form_password.getText().toString();
                if(value_u.isEmpty() || (value_p.isEmpty())){
                    toastMessage("Username / Password is Empty");
                }else{
                    loading_login.setVisibility(View.VISIBLE);
                    action_login(value_u, value_p);
                }


            }
        });


    }
    private void action_login (String _vUsername , String _vPassword) {

        String email = _vUsername;
        String pass = _vPassword;
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("tes", "createUserWithEmail:success");
                    loading_login.setVisibility(View.GONE);
                    onDestroyView();

                } else {
                    loading_login.setVisibility(View.GONE);
                    // If sign in fails, display a message to the user.
                    Log.w(String.valueOf("Login"), "createUserWithEmail:failure", task.getException());
                    toastMessage("Username/Paswword Wrong");


                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View authentiactionLogin = inflater.inflate(R.layout.fragment_authentication_login, container, false);
        ;


        return authentiactionLogin;
    }

    private void toastMessage(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }
}
