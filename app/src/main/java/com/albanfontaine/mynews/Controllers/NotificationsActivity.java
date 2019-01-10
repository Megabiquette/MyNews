package com.albanfontaine.mynews.Controllers;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.albanfontaine.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;

    @BindView(R.id.search_field) EditText mSearchField;
    @BindView(R.id.checkbox_arts) CheckBox mCheckBoxArts;
    @BindView(R.id.checkbox_business) CheckBox mCheckBoxBusiness;
    @BindView(R.id.checkbox_politics) CheckBox mCheckBoxPolitics;
    @BindView(R.id.checkbox_travel) CheckBox mCheckBoxTravel;
    @BindView(R.id.activity_notifications_switch) Switch mSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        ButterKnife.bind(this);

        this.configureToolbar();

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.getId() == R.id.activity_notifications_switch){
                    if(isChecked){
                        // User has enabled notifications
                        if(parametersAreValid()){

                        }else{
                            mSwitch.setChecked(false);
                        }
                    }else{
                        // User has disabled notifications
                    }
                }
            }
        });
    }

    private void configureToolbar(){
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private boolean parametersAreValid(){
        if(mSearchField.getText().toString().trim().isEmpty()){
            Toast.makeText(this, getResources().getString(R.string.verification_search_field), Toast.LENGTH_LONG).show();
            mSearchField.requestFocus();
            return false;
        }
        if(!mCheckBoxArts.isChecked() && !mCheckBoxBusiness.isChecked() &&
                !mCheckBoxPolitics.isChecked() && !mCheckBoxTravel.isChecked()){
            Toast.makeText(this, getResources().getString(R.string.verification_checkbox), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
