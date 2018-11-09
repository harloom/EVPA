package com.example.hx_loom.evpa;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hx_loom.evpa.Adapater.PagerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        listenLogin();
        //
        tabMenu();


    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////
    /* deklarasi buatan */
    private int[] tabSelected = {
            R.drawable.ic_home,
            R.drawable.ic_rank,
            R.drawable.ic_profile
    };

    //    private int[] tabsUnselectedSelected = {
//            R.drawable.ic_home_s,
//            R.drawable.ic_rank_s,
//            R.drawable.ic_profile_s
//    };
    /* fungsi buatan */
    public void tabMenu() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(tabSelected[0]));
        tabLayout.addTab(tabLayout.newTab().setIcon(tabSelected[1]));
        tabLayout.addTab(tabLayout.newTab().setIcon(tabSelected[2]));
        tabLayout.setSelectedTabIndicatorHeight(0);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabTextColors(ColorStateList.valueOf(Color.parseColor("#620E84")));

        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
//                for(int i = 0; i < 3; i++) {
//                    if(i == position)
//                        tabLayout.getTabAt(i).setIcon(tabSelected[i]);
//                    else
//                        tabLayout.getTabAt(i).setIcon(tabsUnselectedSelected[i]);
//                }

                ImageView imageAction = (ImageView) findViewById(R.id.action_add);
                if (tab.getPosition() == 1 || tab.getPosition() == 2){
                    imageAction.setVisibility(View.INVISIBLE);
                }else{
                    imageAction.setVisibility(View.VISIBLE);
                }

                    viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    public void actionAdd(View view) {
        if(mAuth.getCurrentUser().getUid().isEmpty()){
            View fragmentAuth = getLayoutInflater().inflate(R.layout.fragment_authentication_login, null);
            BottomSheetDialog dialog = new BottomSheetDialog(this);
            dialog.setContentView(fragmentAuth);
            dialog.show();
        }





    }

    public void action_login(View view) {
         EditText form_username = (EditText) view.findViewById(R.id.form_username);
         EditText form_password =  (EditText) view.findViewById(R.id.form_password);
        String email = "ilhamwork19@gmail.com";
        toastMessage("Email : " +email);
        String pass = "nokiax6";
        toastMessage("pass : " +pass);
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("tes", "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    toastMessage("nama : " +user.getUid());
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(String.valueOf(MainActivity.this), "createUserWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void toastMessage(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    private void listenLogin(){
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("Succes", "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d("Error", "onAuthStateChanged:signed_out");
                    toastMessage("Successfully signed out.");
                }
                // ...
            }
        };
    }
}
