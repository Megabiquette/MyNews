package com.albanfontaine.mynews.Controllers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.albanfontaine.mynews.R;
import com.albanfontaine.mynews.Utils.AlarmReceiver;
import com.albanfontaine.mynews.Utils.Helper;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.search_field) EditText mSearchField;
    @BindView(R.id.checkbox_arts) CheckBox mCheckBoxArts;
    @BindView(R.id.checkbox_business) CheckBox mCheckBoxBusiness;
    @BindView(R.id.checkbox_politics) CheckBox mCheckBoxPolitics;
    @BindView(R.id.checkbox_travel) CheckBox mCheckBoxTravel;
    @BindView(R.id.activity_notifications_switch) Switch mSwitch;

    // Keys for the Bundle
    private static final String QUERY = "query";
    private static final String CATEGORY = "category";

    SharedPreferences mSharedPreferences;
    // Keys for sharedPreferences
    private static final String SEARCH_FIELD = "searchField";
    private static final String CHECKBOX_ARTS = "checkboxArts";
    public static final String CHECKBOX_BUSINESS = "checkboxBusiness";
    public static final String  CHECKBOX_POLITICS = "checkboxPolitics";
    public static final String CHECKBOX_TRAVEL = "checkboxTravel";
    public static final String SWITCH = "switch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        ButterKnife.bind(this);

        mSharedPreferences = getPreferences(MODE_PRIVATE);

        this.configureToolbar();
        this.restoreCriteria();

        // Set listeners
        mSwitch.setOnCheckedChangeListener(this);
        mCheckBoxArts.setOnCheckedChangeListener(this);
        mCheckBoxBusiness.setOnCheckedChangeListener(this);
        mCheckBoxPolitics.setOnCheckedChangeListener(this);
        mCheckBoxTravel.setOnCheckedChangeListener(this);
        mSearchField.addTextChangedListener(new TextWatcher() {
            // Change in the search field: the user changes the criteria, disable the alarm
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                disableAlarm();
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.getId() == R.id.activity_notifications_switch){
            // Click on the switch
            if(isChecked){
                // User has enabled notifications
                if(Helper.parametersAreValid(this, mSearchField, mCheckBoxArts, mCheckBoxBusiness, mCheckBoxPolitics, mCheckBoxTravel)){
                    setAlarm();
                }else{
                    // Some parameters are missing, disable switch
                    mSwitch.setChecked(false);
                }
            }else{
                // User has disabled notifications
                disableAlarm();
            }
        }else {
            // Click on a checkbox: the user changes the criteria, disable the alarm
            disableAlarm();
        }
    }

    private void setAlarm(){
        // Gets parameters
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

        // Sets an alarm at 11PM
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        // Puts parameters in intent
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        intent.putExtra(QUERY, query);
        intent.putExtra(CATEGORY, category);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void disableAlarm(){
        Intent intent = new Intent(getApplicationContext()  , AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(),0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        mSwitch.setChecked(false);
    }

    @Override
    protected void onStop() {
        // Save the notification criteria
        saveCriteria();
        super.onStop();
    }

    private void saveCriteria(){
        mSharedPreferences.edit().putString(SEARCH_FIELD, mSearchField.getText().toString()).apply();
        mSharedPreferences.edit().putBoolean(CHECKBOX_ARTS, mCheckBoxArts.isChecked()).apply();
        mSharedPreferences.edit().putBoolean(CHECKBOX_BUSINESS, mCheckBoxBusiness.isChecked()).apply();
        mSharedPreferences.edit().putBoolean(CHECKBOX_POLITICS, mCheckBoxPolitics.isChecked()).apply();
        mSharedPreferences.edit().putBoolean(CHECKBOX_TRAVEL, mCheckBoxTravel.isChecked()).apply();
        mSharedPreferences.edit().putBoolean(SWITCH, mSwitch.isChecked()).apply();
    }

    private void restoreCriteria(){
        mSearchField.setText(mSharedPreferences.getString(SEARCH_FIELD, ""));
        mCheckBoxArts.setChecked(mSharedPreferences.getBoolean(CHECKBOX_ARTS, false));
        mCheckBoxBusiness.setChecked(mSharedPreferences.getBoolean(CHECKBOX_BUSINESS, false));
        mCheckBoxPolitics.setChecked(mSharedPreferences.getBoolean(CHECKBOX_POLITICS, false));
        mCheckBoxTravel.setChecked(mSharedPreferences.getBoolean(CHECKBOX_TRAVEL, false));
        mSwitch.setChecked(mSharedPreferences.getBoolean(SWITCH, false));
    }

    ///////////
    // UTILS //
    ///////////

    private void configureToolbar(){
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }
}
