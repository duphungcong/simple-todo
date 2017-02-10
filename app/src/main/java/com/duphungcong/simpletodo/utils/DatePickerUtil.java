package com.duphungcong.simpletodo.utils;

import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;


public class DatePickerUtil {

    public static void dateToDatePicker(Date date, DatePicker dp) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        dp.init(year, month, dayOfMonth, null);
    }

    public static Date datePickerToDate(DatePicker dp) {
        Calendar c = Calendar.getInstance();
        int year = dp.getYear();
        int month = dp.getMonth();
        int dayOfMonth = dp.getDayOfMonth();
        c.set(year, month, dayOfMonth);

        return c.getTime();
    }
}
