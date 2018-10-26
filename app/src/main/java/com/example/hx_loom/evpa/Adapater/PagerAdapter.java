package com.example.hx_loom.evpa.Adapater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.hx_loom.evpa.FragmentHome;
import com.example.hx_loom.evpa.FragmentProfile;
import com.example.hx_loom.evpa.FragmentRank;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private FragmentHome tab1;
    private FragmentRank tab2;
    private FragmentProfile tab3;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                 tab1 =  new FragmentHome();
                return tab1;
            case 1:
                 tab2 = new FragmentRank();
                return  tab2;
            case 2:
                 tab3 = new FragmentProfile();
                return  tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {

        return mNumOfTabs;
    }
}
