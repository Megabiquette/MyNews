package com.albanfontaine.mynews.Controllers;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.albanfontaine.mynews.R;
import com.albanfontaine.mynews.Utils.NetworkAsyncTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment implements NetworkAsyncTask.Listeners {

    @BindView(R.id.fragment_main_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.fragment_main_progressBar) ProgressBar mProgressBar;
    @BindView(R.id.frag_test)
    TextView mTextView;

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

        new NetworkAsyncTask(this).execute("https://api.github.com/users/JakeWharton/following");
        Log.e("TAG", "createView");

        return result;
    }


    //////////////////
    // HTTP REQUEST //
    //////////////////

    @Override
    public void onPreExecute() {
        this.mProgressBar.setVisibility(View.VISIBLE);
        Log.e("TAG", "onPreExecute_FRAGMENT");
    }

    @Override
    public void doInBackground() {
        Log.e("TAG", "doInBackground_FRAGMENT");
    }

    @Override
    public void onPostExecute(String response) {
        this.mProgressBar.setVisibility(View.GONE);
        this.mTextView.setText(response);
        ((MainActivity) getActivity()).mPager.getAdapter().notifyDataSetChanged();
        Log.e("TAG", "onPostExecute_FRAGMENT");
    }
}
