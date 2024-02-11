package com.example.complaintregistration.Util;

import android.annotation.SuppressLint;
import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeHelper
{
    public static String getCurrentDate()
    {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().getTime());
    }

    public static String getFormattedDate()
    {
        return new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime());
    }

    public static String getCurrentDateTime()
    {
        return String.valueOf(DateFormat.format("yyyy-MM-dd HH:mm:ss", new Date().getTime()));
    }

    public static String getCurrentDateWithFormat()
    {
        return new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime());
    }

    public static String convertStringToDate(String date)
    {
        Date convertedDate = null;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            convertedDate = format.parse(date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        assert convertedDate != null;
//        return String.valueOf(DateFormat.format("yyyy-MM-dd", convertedDate)).concat(" " + new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));
        return String.valueOf(DateFormat.format("yyyy-MM-dd", convertedDate)).concat(" 00:00:00.000");
    }

    @SuppressLint("SimpleDateFormat")
    public static Date convertStringToDateFormat(String s)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        Date date = null;
        try
        {
            date = formatter.parse(s);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return date;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getCalculatedDate(int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, days);
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date(cal.getTimeInMillis()));
    }
}