package com.albanfontaine.mynews.Controllers;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.albanfontaine.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HelpActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.help_intro_title) TextView introTitle;
    @BindView(R.id.help_intro_text) TextView introText;
    @BindView(R.id.help_main_title) TextView mainTitle;
    @BindView(R.id.help_main_text) TextView mainText;
    @BindView(R.id.help_search_title) TextView searchTitle;
    @BindView(R.id.help_search_text) TextView searchText;
    @BindView(R.id.help_notifications_title) TextView notificationsTitle;
    @BindView(R.id.help_notifications_text) TextView notificationsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);

        this.configureToolbar();
    }

    private void configureToolbar(){
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }
}
