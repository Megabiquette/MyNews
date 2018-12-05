package com.albanfontaine.mynews.Controllers;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albanfontaine.mynews.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    // 1 - Create keys for our Bundle
    private static final String KEY_POSITION="position";


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

}
