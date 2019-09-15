package com.gmail.vdomasapp.weathero.utils;

import android.content.Context;

import androidx.preference.PreferenceManager;

public final class SharedPrefHelper {

    //Save shared preferences integers
    public static void saveSharedPrefIntValues(Context context, String key, int intValue) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(key, intValue)
                .apply();
    }

    //Save shared preferences string value
    public static void saveSharedPrefStringValues(Context context, String key, String stringValue) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(key, stringValue)
                .apply();
    }

    //Save shared preferences boolean value
    public static void saveSharedPrefBooleanValues(Context context, String key, boolean booleanValue) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(key, booleanValue)
                .apply();
    }

}
