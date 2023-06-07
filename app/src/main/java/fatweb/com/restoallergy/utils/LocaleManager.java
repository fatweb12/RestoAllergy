package com.fatweb.allergysafenz.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.Locale;

public class LocaleManager {

    public static final String LANGUAGE_ENGLISH = "en";
    public static final String LANGUANGE_SPANISH = "es";
    public static final String LANGUAGE_KOREA = "ko";
    public static final String LANGUAGE_JAPANESE = "ja";
    public static final String LANGUAGE_CHINESE_SM = "zh-Hans";
    public static final String LANGUAGE_CHINESE_TR = "zh-Hant";
    public static final String LANGUAGE_GERMAN = "de";
    public static final String LANGUAGE_FRENCH = "fr";


    public static final String LANGUAGE_FILLIPNO = "fil";
    public static final String LANGUAGE_GREEK = "el";
    public static final String LANGUAGE_HINDI = "hi";
    public static final String LANGUAGE_INDONESIAN = "id";
    public static final String LANGUAGE_ITALIAN = "it";
    public static final String LANGUAGE_IRISH = "ga";
    public static final String LANGUAGE_LATIN = "la";
    public static final String LANGUAGE_MOARI = "mi";
    public static final String LANGUAGE_NEPALI = "ne";
    public static final String LANGUAGE_PORTUNESE = "pt";
    public static final String LANGUAGE_RUSIAN = "ru";
    public static final String LANGUAGE_SOAMON = "sm";
    public static final String LANGUAGE_SWEDISH = "sv";
    public static final String LANGUAGE_THAI = "th";
    public static final String LANGUAGE_TURKISH = "tr";
    public static final String LANGUAGE_URDU = "ur";
    public static final String LANGUAGE_VENTANESE= "vi";



    private static final String LANGUAGE_KEY = "language_key";

    public static Context setLocale(Context c) {
        return updateResources(c, getLanguage(c));
    }

    public static Context setNewLocale(Context c, String language) {
        persistLanguage(c, language);
        return updateResources(c, language);
    }

    public static String getLanguage(Context c) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
        return prefs.getString(LANGUAGE_KEY, LANGUAGE_ENGLISH);
    }

    @SuppressLint("ApplySharedPref")
    private static void persistLanguage(Context c, String language) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
        // use commit() instead of apply(), because sometimes we kill the application process immediately
        // which will prevent apply() to finish
        prefs.edit().putString(LANGUAGE_KEY, language).commit();
    }

    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }

    public static Locale getLocale(Resources res) {
        Configuration config = res.getConfiguration();
        return Build.VERSION.SDK_INT >= 24 ? config.getLocales().get(0) : config.locale;
    }
}