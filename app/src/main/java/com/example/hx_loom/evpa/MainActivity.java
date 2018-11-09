package com.example.hx_loom.evpa;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;

import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.hx_loom.evpa.Adapater.PagerAdapter;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        tabMenu();


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

        View fragmentAuth = getLayoutInflater().inflate(R.layout.fragment_authentication_login, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(fragmentAuth);
        dialog.show();


    }
}
