package com.gmail.vdomasapp.weathero;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

import static com.gmail.vdomasapp.weathero.utils.Constants.CHECK_BOX_CRASH_REPORTS;

public class WeatheroApp extends Application {

    private static WeatheroApp weatheroAppInstance;
    private SharedPreferences sharedPreferences;
    private boolean crashlyticsEnabled;

    @Override
    public void onCreate() {
        super.onCreate();
        weatheroAppInstance = this;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getInstance());
        crashlyticsEnabled = sharedPreferences.getBoolean(CHECK_BOX_CRASH_REPORTS, true);

        //Check if crash reports is enabled in settings
        if (crashlyticsEnabled) {
            Fabric.with(weatheroAppInstance, new Crashlytics());
        }
    }

    //get instance of Weathero application
    public static synchronized WeatheroApp getInstance() {
        return weatheroAppInstance;
    }
}
