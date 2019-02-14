package com.albanfontaine.mynews.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.albanfontaine.mynews.R;

//Utility class
public class Helper {

    /**
     * Puts the date in "DD/MM/YYYY" format in a TextView from a DatePicker
     *
     * @param yearInt the year returned by the DatePicker
     * @param monthInt the month returned by the DatePicker (starts at 0)
     * @param dayInt the day returned by the DatePicker
     * @param textView the TextView to put the date in
     * @return the date in "DD/MM/YYYY" format
     */
    public static String processDate(int yearInt, int monthInt, int dayInt, TextView textView){
        String year = Integer.toString(yearInt);
        String month = (monthInt+1 < 10) ? "0" + Integer.toString(monthInt+1) : Integer.toString(monthInt+1);
        String day = (dayInt < 10) ? "0" + Integer.toString(dayInt) : Integer.toString(dayInt);
        textView.setText(day+"/"+month+"/"+year);
        return year+month+day;
    }

    /**
     * Puts the date from "YYYY-MM-DD" to "DD/MM/YY" format
     *
     * @param date the date in "YYY-MM-DD" format
     * @return the date in "DD/MM/YY" format
     */
    public static String formatDate(String date){
        return date.substring(8, 10)+"/"+date.substring(5, 7)+"/"+date.substring(2,4);
    }

    /**
     * Checks the internet connection
     *
     * @param context the current context
     * @return a boolean set to true if the device is connected to the internet
     */
    public static boolean isConnectedToInternet(Context context){
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = cm.getActiveNetworkInfo();
        return network != null && network.isConnectedOrConnecting();
    }

    /**
     *  Checks the validity of the form
     *
     *  The form is valid if the search field is not empty and
     *  at least one checkbox is checked
     *
     * @param context the current context
     * @param searchField the search field that must be checked
     * @param cbArts one of the checkboxes
     * @param cbBusiness one of the checkboxes
     * @param cbPolitics one of the checkboxes
     * @param cbTravel one of the checkboxes
     * @return a boolean set to true if the form is valid
     */
    public static boolean parametersAreValid(Context context, EditText searchField, CheckBox cbArts, CheckBox cbBusiness, CheckBox cbPolitics, CheckBox cbTravel){
        if(searchField.getText().toString().trim().isEmpty()){
            Toast.makeText(context, context.getResources().getString(R.string.verification_search_field), Toast.LENGTH_LONG).show();
            searchField.requestFocus();
            return false;
        }
        if(!cbArts.isChecked() && !cbBusiness.isChecked() &&
                !cbPolitics.isChecked() && !cbTravel.isChecked()){
            Toast.makeText(context, context.getResources().getString(R.string.verification_checkbox), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    /**
     * Checks if the the begin date is anterior to the end date in the Search form
     *
     * @param context the current context
     * @param beginDate the begin date in "YYYYMMDD format
     * @param endDate the end date in "YYYYMMDD" format
     * @return a boolean set to true is the begin date is anterior to the end date
     */
    public static boolean datesAreValid(Context context, String beginDate, String endDate){
        if(!beginDate.isEmpty() && !endDate.isEmpty() && Integer.parseInt(beginDate) > Integer.parseInt(endDate)){
            Toast.makeText(context, context.getResources().getString(R.string.verification_dates), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
