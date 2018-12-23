package com.example.hx_loom.evpa;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hx_loom.evpa.Adapater.PagerAdapter;
import com.example.hx_loom.evpa.Auth.Authentication_login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Authentication_login login = new Authentication_login();
    FragmentManager fragmentManagerLogin = getSupportFragmentManager();
    FirebaseUser user ;


    static final String STATE_BERANDA = "Home";
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if(savedInstanceState != null){
//
//        }
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
        View tab1  = getLayoutInflater().inflate(R.layout.custom_tab , null);
        tab1.findViewById(R.id.iconstab).setBackgroundResource(tabSelected[0]);
        View tab2  = getLayoutInflater().inflate(R.layout.custom_tab , null);
        tab2.findViewById(R.id.iconstab).setBackgroundResource(tabSelected[1]);
        View tab3  = getLayoutInflater().inflate(R.layout.custom_tab , null);
        tab3.findViewById(R.id.iconstab).setBackgroundResource(tabSelected[2]);


        final ViewPager viewPager = findViewById(R.id.pager);
        final TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setCustomView(tab1));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tab2));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tab3));
//        tabLayout.setSelectedTabIndicatorHeight(0);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabTextColors(ColorStateList.valueOf(getResources().getColor(R.color.oneesan)));



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


                ImageView imageAction = findViewById(R.id.action_add);
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
                if (tab.getPosition() == 0){
//                    FragmentManager a = getSupportFragmentManager();
//                    FragmentHome fragmentHome = (FragmentHome) a.findFragmentByTag("tab1");
//                    fragmentHome.recyclerView.getLayoutManager().scrollToPosition(0);
                }
            }
        });
    }

    public void actionAdd(View view) {

        if (mAuth.getCurrentUser() == null) {
           loginShow();

        }else{
            Intent showFormPost = new Intent(view.getContext(),PostEventFormActivity.class);
            showFormPost.putExtra("getID",user.getUid());
            showFormPost.putExtra("getNameid",user.getDisplayName());
            startActivity(showFormPost);

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
                 user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("Succes", "onAuthStateChanged:signed_in:" + user.getUid());
//                    toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d("Error", "onAuthStateChanged:signed_out");
//                    toastMessage("Successfully signed out.");
                    if(login != null){
                        getSupportFragmentManager().beginTransaction().remove(login).commit();
                    }
                }
                // ...
            }
        };
    }
}
