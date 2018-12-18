package com.albanfontaine.mynews.Views;

import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.albanfontaine.mynews.Controllers.MainFragment;

public class PageAdapter extends FragmentPagerAdapter {
    private NavigationView mNavigationView;

    public PageAdapter(FragmentManager mgr){
        super(mgr);
    }

    @Override
    public Fragment getItem(int position) {
        return MainFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch(position){
            case 0:
                title = "Top Stories";
                break;
            case 1:
                title = "Most Popular";
                break;
            case 2:
                title = "Arts";
                break;
            case 3:
                title = "Business";
                break;
            case 4:
                title = "Politics";
                break;
            case 5:
                title = "Travel";
                break;
        }
        return title;
    }
}
