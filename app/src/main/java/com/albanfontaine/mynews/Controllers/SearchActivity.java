package com.albanfontaine.mynews.Controllers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.albanfontaine.mynews.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.search_field) EditText mSearchField;
    @BindView(R.id.activity_search_spinner_begin) TextView mSpinnerBegin;
    @BindView(R.id.activity_search_spinner_end) TextView mSpinnerEnd;
    @BindView(R.id.activity_search_button_search) Button mButton;
    @BindView(R.id.checkbox_arts) CheckBox mCheckBoxArts;
    @BindView(R.id.checkbox_business) CheckBox mCheckBoxBusiness;
    @BindView(R.id.checkbox_politics) CheckBox mCheckBoxPolitics;
    @BindView(R.id.checkbox_travel) CheckBox mCheckBoxTravel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        mSpinnerBegin.setOnClickListener(this);
        mSpinnerEnd.setOnClickListener(this);
        mButton.setOnClickListener(this);

        this.configureToolbar();
    }

    private void configureToolbar(){
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        // Gets the current day to show as default in the date picker
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker;
        switch (id){
            case R.id.activity_search_spinner_begin:
                datePicker = new DatePickerDialog(this, datePickerListenerBegin, year, month, day);
                datePicker.show();
                break;
            case R.id.activity_search_spinner_end:
                datePicker = new DatePickerDialog(this, datePickerListenerEnd, year, month, day);
                datePicker.show();
                break;
            case R.id.activity_search_button_search:

                break;
        }
    }

    ////////////////////////////////////
    // Listeners for the date pickers //
    ////////////////////////////////////
    
    private DatePickerDialog.OnDateSetListener datePickerListenerBegin
            = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year,
                              int month, int day) {
            mSpinnerBegin.setText(day+"/"+(month+1)+"/"+year);
        }
    };

    private DatePickerDialog.OnDateSetListener datePickerListenerEnd
            = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year,
                              int month, int day) {
            mSpinnerEnd.setText(day+"/"+(month+1)+"/"+year);
        }
    };

}
