package com.example.myweather.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtils {

    public static String addDays(Date date, int days){
        String result = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM");
        result = format.format(cal.getTime());
        return result;
    }

}
