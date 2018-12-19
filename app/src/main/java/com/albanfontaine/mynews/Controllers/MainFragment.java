package com.albanfontaine.mynews.Controllers;


import android.os.Bundle;
import android.support.annotation.NonNull;
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
    @BindView(R.id.frag_test) TextView mTextView;

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

        // Gets which tab is currently viewed
        int position = getArguments().getInt(KEY_POSITION, 0);

        new NetworkAsyncTask(this).execute(buildRequest(position));


        return result;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    //////////////////
    // HTTP REQUEST //
    //////////////////

    @Override
    public void onPreExecute() {
        this.mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void doInBackground() {
    }

    @Override
    public void onPostExecute(String response) {
        this.mProgressBar.setVisibility(View.GONE);
        this.mTextView.setText(response);
    }

    private String buildRequest(int position){
        StringBuilder request = new StringBuilder();
        if (position == 0){
            // Top Stories
            request.append("https://api.nytimes.com/svc/topstories/v2/home.json?api-key=190abb26a35547f29b03a63c6c5bf084");
        } else if(position == 1){
            // Most Popular
            request.append("https://api.nytimes.com/svc/mostpopular/v2/mostviewed/all-sections/7.json?api-key=190abb26a35547f29b03a63c6c5bf084");
        } else{
            request.append("http://api.nytimes.com/svc/search/v2/articlesearch.json?fq=news_desk:");
            switch (position){
                case 2: // Arts
                    request.append("(\"Arts\")");
                    break;
                case 3: // Business
                    request.append("(\"Business\")");
                    break;
                case 4: // Politics
                    request.append("(\"Politics\")");
                    break;
                case 5: // Travel
                    request.append("(\"Travel\")");
                    break;
            }
            // Sort by newest and filter fields
            request.append("&sort=newest&fl=web_url,snippet,news_desk,multimedia,pub_date&api-key=190abb26a35547f29b03a63c6c5bf084");
        }

        return request.toString();
    }
}
