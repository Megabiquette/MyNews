package com.albanfontaine.mynews.Views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.albanfontaine.mynews.Controllers.MainFragment;

public class PageAdapter extends FragmentPagerAdapter {

    public PageAdapter(FragmentManager mgr){
        super(mgr);
    }

    @Override
    public Fragment getItem(int tab) {
        return MainFragment.newInstance(tab);
    }

    @Override
    public int getCount() {
        return 6;
    }
}
