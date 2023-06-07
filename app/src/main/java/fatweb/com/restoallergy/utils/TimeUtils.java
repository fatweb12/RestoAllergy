package com.fatweb.allergysafenz.utils;



import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtils {

    public static boolean ifMoreThanADay(String time) {

        try {
            Locale locale = new Locale(LocaleManager.LANGUAGE_ENGLISH);
            final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
            Date date1 = sdf.parse(time.replace('T', ' '));
            Date date2 = sdf.parse(getCurrentProperDateFormat(locale));
            boolean moreThanADay = Math.abs(date1.getTime() - date2.getTime()) > MILLIS_PER_DAY;
            return moreThanADay;
        } catch (ParseException pe) {

        }
        return false;
    }

    public static boolean ifPastDate(String time) {

        try {
            Locale locale = new Locale(LocaleManager.LANGUAGE_ENGLISH);
            if (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale).parse(time.replace('T', ' ')).before(new Date())) {
                return true;
            }
        } catch (ParseException pe) {

        }
        return false;
    }


    public static String getCalendarProperFormat(int year, int month, int day) {
        Locale locale = new Locale(LocaleManager.LANGUAGE_ENGLISH);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month, day);
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd yyyy", locale);
        return sdf.format(date);
    }

    public static String getCalendarFlightDateFormat(int year, int month, int day) {
        Locale locale = new Locale(LocaleManager.LANGUAGE_ENGLISH);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month, day);
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", locale);
        return sdf.format(date);
    }

    public static String getCurrentProperDateFormat(Locale locale) {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
        return df.format(c);
    }

    public static String getCurrentDate() {
        Locale locale = new Locale(LocaleManager.LANGUAGE_ENGLISH);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MMMM dd, yyyy", locale);
        return df.format(c);
    }

    public static String getExpiryDate() {
        Locale locale = new Locale(LocaleManager.LANGUAGE_ENGLISH);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 12);
        Date start = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("MMMM dd yyyy", locale);
        return df.format(start);
    }

    public static String getExpiryMonth() {
        Locale locale = new Locale(LocaleManager.LANGUAGE_ENGLISH);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        Date start = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("MMMM dd yyyy", locale);
        return df.format(start);
    }


    public static boolean checkIfPassportExpiryIsValid(String date) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 6);
        Date start = cal.getTime();
        Date end = convertStringToDate(date);
        if (end == null) {
            return false;
        } else {
            return end.after(start);
        }
    }

    public static boolean checkIfPassportExpiryIsValid3months(String date) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 3);
        Date start = cal.getTime();
        Date end = convertStringToDate(date);
        if (end == null) {
            return false;
        } else {
            return end.after(start);
        }
    }

    public static String convertYYYYMMDDFormat(String date) {
        Locale locale = new Locale(LocaleManager.LANGUAGE_ENGLISH);
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("MMMM dd yyyy", locale);
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy/MM/dd", locale);
            Date date1 = format1.parse(date);
            return format2.format(date1);
        } catch (ParseException pe) {
            return date;
        }

    }

    public static String properDateFormatInBooking(String date) {
        Locale locale = new Locale(LocaleManager.LANGUAGE_ENGLISH);
        try {
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd", locale);
            SimpleDateFormat format1 = new SimpleDateFormat("MMMM dd, yyyy", locale);
            Date date1 = format1.parse(date.replace('T', ' '));
            return format2.format(date1);
        } catch (ParseException pe) {
            return date;
        }

    }

    public static String newMembersDateFormat(String date) {
        Locale locale = new Locale(LocaleManager.LANGUAGE_ENGLISH);
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd", locale);
            SimpleDateFormat format2 = new SimpleDateFormat("MMMM dd yyyy", locale);
            Date date1 = format1.parse(date.replace('T', ' '));
            return format2.format(date1);
        } catch (ParseException pe) {
            return null;
        }

    }

    public static String properDateFormat(String date) {
        Locale locale = new Locale(LocaleManager.LANGUAGE_ENGLISH);
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm", locale);
            SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy", locale);
            SimpleDateFormat format3 = new SimpleDateFormat("HH:mm", locale);
            format1.setTimeZone(getUTCTimeZone());
            Date date1 = format1.parse(date.replace('T', ' '));
            format1.setTimeZone(getTimeZone());
            return format2.format(date1) + " at " + format3.format(date1);
        } catch (ParseException pe) {
            return date;
        }
    }

    public static String properDateFormatFeed(String date, Context context) {
        Locale locale = new Locale(LocaleManager.getLanguage(context));
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
            SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy", locale);
            SimpleDateFormat format3 = new SimpleDateFormat("HH:mm", locale);
            format1.setTimeZone(getUTCTimeZone());
            Date date1 = format1.parse(date.replace('T', ' '));
            format1.setTimeZone(getTimeZone());
            return format2.format(date1) + " at " + format3.format(date1);
        } catch (ParseException pe) {
            return date;
        }


    }

    public static String properDateFormatFeed(String date) {
        Locale locale = new Locale(LocaleManager.LANGUAGE_ENGLISH);
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
            SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy", locale);
            SimpleDateFormat format3 = new SimpleDateFormat("HH:mm", locale);
            format1.setTimeZone(getUTCTimeZone());
            Date date1 = format1.parse(date.replace('T', ' '));
            format1.setTimeZone(getTimeZone());
            return format2.format(date1) + " at " + format3.format(date1);
        } catch (ParseException pe) {
            return date;
        }


    }

    public static String properMembershipDateFormat(String date) {
        Locale locale = new Locale(LocaleManager.LANGUAGE_ENGLISH);
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
            SimpleDateFormat format2 = new SimpleDateFormat("dd MMM yyyy", locale);
            format1.setTimeZone(getUTCTimeZone());
            Date date1 = format1.parse(date.replace('T', ' '));
            format1.setTimeZone(getTimeZone());
            return format2.format(date1);
        } catch (ParseException pe) {
            return date;
        }
    }


    public static String properFlightDateFormat(String date) {
        Locale locale = new Locale(LocaleManager.LANGUAGE_ENGLISH);
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
            SimpleDateFormat format2 = new SimpleDateFormat("MMMM dd yyyy", locale);
            format1.setTimeZone(getUTCTimeZone());
            Date date1 = format1.parse(date.replace('T', ' '));
            format1.setTimeZone(getTimeZone());
            return format2.format(date1);
        } catch (ParseException pe) {
            return date;
        }
    }

    public static String dateTimeOnly(String date) {
        Locale locale = new Locale(LocaleManager.LANGUAGE_ENGLISH);
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
            SimpleDateFormat format3 = new SimpleDateFormat("HH:mm", locale);
            format1.setTimeZone(getUTCTimeZone());
            Date date1 = format1.parse(date.replace('T', ' '));
            format1.setTimeZone(getTimeZone());
            return format3.format(date1);
        } catch (ParseException pe) {
            return date;
        }
    }

    public static String properDateFormatWithTime(String date) {
        Locale locale = new Locale(LocaleManager.LANGUAGE_ENGLISH);
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
            SimpleDateFormat format2 = new SimpleDateFormat("MMMM dd, yyyy", locale);
            SimpleDateFormat format3 = new SimpleDateFormat("HH:mm", locale);
            format1.setTimeZone(getUTCTimeZone());
            Date date1 = format1.parse(date.replace('T', ' '));
            format1.setTimeZone(getTimeZone());
            return format2.format(date1) + " at " + format3.format(date1);
        } catch (ParseException pe) {
            return date;
        }

    }


    public static Date convertStringToDate(String date) {
        Locale locale = new Locale(LocaleManager.LANGUAGE_ENGLISH);
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd", locale);
        try {
            format.setLenient(false);
            Date d = format.parse(date);
            return d;
        } catch (ParseException e) {
            return null;
        }

    }

    public static String getCurrentTime() {
        Locale locale = new Locale(LocaleManager.LANGUAGE_ENGLISH);
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", locale);
        String dateText = df2.format(date);
        return dateText;
    }

    public static TimeZone getTimeZone() {
        return TimeZone.getDefault();
    }

    public static TimeZone getUTCTimeZone() {
        return TimeZone.getTimeZone("UTC");
    }



    public static String getCurrentDateNewFormat() {
        Locale locale = new Locale(LocaleManager.LANGUAGE_ENGLISH);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy",locale);
        return df.format(c);
    }

    public static String newDateFormat(String date) {
        Locale locale = new Locale(LocaleManager.LANGUAGE_ENGLISH);
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("MMMM dd, yyyy",locale);
            SimpleDateFormat format3 =  new SimpleDateFormat("MM-dd-yyyy",locale);
            Date date1 = format1.parse(date);
            return format3.format(date1);
        } catch (ParseException pe) {
            return date;
        }
    }
}
