package com.albanfontaine.mynews.Controllers;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import android.widget.Toast;

import com.albanfontaine.mynews.R;
import com.albanfontaine.mynews.Utils.Helper;

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

    // Keys for the Bundle
    private static final String QUERY = "query";
    private static final String CATEGORY = "category";
    private static final String BEGIN_DATE = "beginDate";
    private static final String END_DATE = "endDate";

    private String mBeginDate = "";
    private String mEndDate = "";

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
                if(Helper.parametersAreValid(this, mSearchField, mCheckBoxArts, mCheckBoxBusiness, mCheckBoxPolitics, mCheckBoxTravel)) {
                    this.gatherParametersAndLaunchResultActivity();
                }
                break;
        }
    }

    private void gatherParametersAndLaunchResultActivity(){
        // Gets the parameters for the query
        String query = mSearchField.getText().toString().trim();
        String category = "news_desk:(";
            if (mCheckBoxArts.isChecked())
                category += "\"Arts\" ";
            if (mCheckBoxBusiness.isChecked())
                category += "\"Business\" ";
            if (mCheckBoxPolitics.isChecked())
                category += "\"Politics\" ";
            if (mCheckBoxTravel.isChecked())
                category += "\"Travel\"";
            category += ")";

        // Launch the request in another activity
        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra(QUERY, query);
        intent.putExtra(CATEGORY, category);
        intent.putExtra(BEGIN_DATE, mBeginDate);
        intent.putExtra(END_DATE, mEndDate);
        startActivity(intent);
    }

    ////////////////////////////////////
    // Listeners for the date pickers //
    ////////////////////////////////////

    private DatePickerDialog.OnDateSetListener datePickerListenerBegin
            = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year,
                              int month, int day) {
            mBeginDate = Helper.processDate(year, month, day, mSpinnerBegin);
        }
    };

    private DatePickerDialog.OnDateSetListener datePickerListenerEnd
            = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year,
                              int month, int day) {
            mEndDate = Helper.processDate(year, month, day, mSpinnerEnd);
        }
    };
}
