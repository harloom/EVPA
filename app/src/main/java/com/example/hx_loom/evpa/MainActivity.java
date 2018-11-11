package com.example.hx_loom.evpa;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
    private Authentication_login login = new Authentication_login();
    FragmentManager fragmentManagerLogin = getSupportFragmentManager();


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
                if (tab.getPosition() == 1 || tab.getPosition() == 2) {
                    imageAction.setVisibility(View.INVISIBLE);
                } else {
                    imageAction.setVisibility(View.VISIBLE);
                }

                    //validation login
                if (mAuth.getCurrentUser() == null && tab.getPosition() == 2) {

                    loginShow();
                    tabLayout.getTabAt(0).select();

                } else {
                    viewPager.setCurrentItem(tab.getPosition());
                }


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

        if (mAuth.getCurrentUser() == null) {
           loginShow();

        }


    }
    private void loginShow(){
        login.show(fragmentManagerLogin, "Login");
    }


    private void toastMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void listenLogin() {
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
                    if(login != null){
                        getSupportFragmentManager().beginTransaction().remove(login).commit();
                    }
                }
                // ...
            }
        };
    }
}
