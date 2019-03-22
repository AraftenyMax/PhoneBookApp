package com.maxdev.maxphonebook.utils;

import android.util.Log;

import com.maxdev.maxphonebook.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateFormatter {
    private final static String tag = "DateFormatter";
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    public static Date fromString(String s) throws ParseException {
        try {
            return dateFormat.parse(s);
        } catch (ParseException ex) {
            Log.e(tag, "Unable to format date:" + s);
            throw new ParseException(ex.getMessage(), 0);
        }
    }

    public static String toString(Date date) {
        return dateFormat.format(date);
    }
}
