package com.albanfontaine.mynews;

import android.widget.TextView;

import com.albanfontaine.mynews.Utils.Helper;

import org.junit.Test;

import static org.junit.Assert.*;


public class UnitTest {

    // Put the date in MM/DD/YY format for article display
    @Test
    public void dateFormattingTest(){
        String date = "2019-02-04T12:23:13-05:00";
        String result = Helper.formatDate(date);

        assertEquals("04/02/19", result);
    }

}