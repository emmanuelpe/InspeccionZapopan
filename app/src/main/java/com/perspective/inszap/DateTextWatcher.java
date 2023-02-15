package com.perspective.inszap;

import android.text.Editable;
import android.text.TextWatcher;

import java.util.Calendar;
import java.util.Locale;

public class DateTextWatcher implements TextWatcher {
    public static final int MAX_FORMAT_LENGTH = 8;
    public static final int MIN_FORMAT_LENGTH = 3;

    private String updatedText;
    private boolean editing;
    private Calendar cal = Calendar.getInstance();

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.toString().equals(updatedText) || editing) return;

        String digitsOnly = charSequence.toString().replaceAll("\\D", "");
        int digitLen = digitsOnly.length();

        if (digitLen < MIN_FORMAT_LENGTH || digitLen > MAX_FORMAT_LENGTH) {
            updatedText = digitsOnly;
            return;
        }

        if (digitLen <= 4) {
            String day = digitsOnly.substring(0, 2);
            String month = digitsOnly.substring(2);

            updatedText = String.format(Locale.US, "%s-%s", day, month);
        }
        else {
            String day = digitsOnly.substring(0, 2);
            String month = digitsOnly.substring(2, 4);
            String year = digitsOnly.substring(4);


        if(Integer.valueOf(month) > 12) {
           month="12";
        }
        if(Integer.valueOf(year) >= 2100) {
             year="2100";

        }


            if(Integer.valueOf(month) == 2 && Integer.valueOf(day) >29  ) {
                day="28";
            }
            if(Integer.valueOf(month) == 1 && Integer.valueOf(day) >31) {
                day="31";
            }
            if(Integer.valueOf(month) ==3  && Integer.valueOf(day) >31) {
                day="31";
            }
            if(Integer.valueOf(month) ==4  && Integer.valueOf(day) >30) {
                day="30";
            }
            if(Integer.valueOf(month) ==5  && Integer.valueOf(day) >31) {
                day="31";
            }
            if(Integer.valueOf(month) ==6  && Integer.valueOf(day) >30) {
                day="30";
            }
            if(Integer.valueOf(month) ==7  && Integer.valueOf(day) >31) {
                day="31";
            }
            if(Integer.valueOf(month) ==8  && Integer.valueOf(day) >31) {
                day="31";
            }
            if(Integer.valueOf(month) ==9  && Integer.valueOf(day) >30) {
                day="30";
            }
            if(Integer.valueOf(month) ==10  && Integer.valueOf(day) >31) {
                day="31";
            }
            if(Integer.valueOf(month) ==11  && Integer.valueOf(day) >30) {
                day="30";
            }
            if(Integer.valueOf(month) ==12  && Integer.valueOf(day) >31) {
                day="31";
            }
            if(Integer.valueOf(day)==0 ){
                day="01";
            }
            if(Integer.valueOf(month)==0 ){
                month="01";
            }
            if(Integer.valueOf(year)==0 ){
                year="2021";
            }


            updatedText = String.format(Locale.US, "%s-%s-%s",day, month, year);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editing) return;

        editing = true;

        editable.clear();
        editable.insert(0, updatedText);

        editing = false;
    }
}
