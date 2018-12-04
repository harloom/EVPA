package com.example.hx_loom.evpa.Auth;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hx_loom.evpa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class RegisterActivity extends AppCompatActivity {

//    private String email;
//    private String password;
//    private String password2nd;
    private Boolean isEmailuse;

    protected EditText txt_email, txt_pass, txt_vPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txt_email = (EditText) findViewById(R.id.reg_email);
        txt_pass = (EditText) findViewById(R.id.reg_password);
        txt_vPassword = (EditText) findViewById(R.id.reg_password_v);


        findViewById(R.id.button_reg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txt_email.getText().toString().isEmpty() || txt_email.getText().toString().isEmpty() || txt_email.getText().toString().isEmpty()) {
                    toasMassage("Register Failed");
                } else {
                    if (txt_pass.getText().toString().equals(txt_vPassword.getText().toString())) {
                        toasMassage("verikasi Password not Match");
                    } else {
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("Users").whereEqualTo("email",txt_email.getText().toString())
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                isEmailuse = true;
                                toasMassage("Email sudah digunakan");
                            }
                        });

                        if(!isEmailuse){
                            Intent intent = new Intent(v.getContext(),RegSetInfo.class);
                            intent.putExtra("email",txt_email.getText().toString());
                            intent.putExtra("password",txt_pass.getText().toString());
                            intent.putExtra("v_password",txt_vPassword.getText().toString());
                            startActivity(intent);
                        }
                    }
                }


            }
        });
    }

    private void toasMassage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


    public void backMenu(View view) {
        finish();
    }
}
