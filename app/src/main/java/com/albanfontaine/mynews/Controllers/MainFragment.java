package com.albanfontaine.mynews.Controllers;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.albanfontaine.mynews.R;
import com.albanfontaine.mynews.Utils.NetworkAsyncTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements NetworkAsyncTask.Listeners {

    @BindView(R.id.fragment_main_rootview) LinearLayout mRootView;
    @BindView(R.id.fragment_main_recycler_view) RecyclerView mRecyclerView;

    // Create keys for our Bundle
    private static final String KEY_POSITION="position";


    public MainFragment() { }

    public static MainFragment newInstance(int position){
        MainFragment frag = new MainFragment();

        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, result);




        return result;
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void doInBackground() {

    }

    @Override
    public void onPostExecute(String success) {

    }
}
