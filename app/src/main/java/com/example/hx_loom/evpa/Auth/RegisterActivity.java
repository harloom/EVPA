package com.example.hx_loom.evpa.Auth;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hx_loom.evpa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class RegisterActivity extends AppCompatActivity {

    //    private String email;
//    private String password;
//    private String password2nd;

    ProgressBar loading_register;
    protected EditText txt_email, txt_pass, txt_vPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loading_register = (ProgressBar) findViewById(R.id.loading_register) ;
        txt_email = (EditText) findViewById(R.id.reg_email);
        txt_pass = (EditText) findViewById(R.id.reg_password);
        txt_vPassword = (EditText) findViewById(R.id.reg_password_v);


        findViewById(R.id.button_reg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                loading_register.setVisibility(View.VISIBLE);
                if (txt_email.getText().toString().isEmpty() || txt_pass.getText().toString().isEmpty() || txt_vPassword.getText().toString().isEmpty()
                        || !txt_email.getText().toString().contains("@")) {
                    loading_register.setVisibility(View.GONE);
                    toasMassage("Register Failed");
                } else if (txt_pass.getText().length()<6 ||txt_vPassword.getText().length() <6 ) {
                    loading_register.setVisibility(View.GONE);
                    toasMassage("Passowrd Minimal 6 Karakter");
                } else if (!(txt_pass.getText().toString().equals(txt_vPassword.getText().toString()))) {
                    loading_register.setVisibility(View.GONE);
                    toasMassage("verikasi Password not Match");
                } else {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("Users").whereEqualTo("email", txt_email.getText().toString())
                            .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                            Log.d("Cek Doucment",queryDocumentSnapshots.getDocuments()+"");
                            if(queryDocumentSnapshots.getDocuments().isEmpty()){
                                Intent intent = new Intent(v.getContext(), RegSetInfo.class);
                                intent.putExtra("email", txt_email.getText().toString());
                                intent.putExtra("password", txt_pass.getText().toString());
                                intent.putExtra("v_password", txt_vPassword.getText().toString());
                                startActivity(intent);
                            }else{
                                toasMassage("Email Sudah Di gunakan");
                            }
                            loading_register.setVisibility(View.GONE);
                        }
                    });





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
