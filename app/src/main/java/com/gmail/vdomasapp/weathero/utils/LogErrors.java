package com.gmail.vdomasapp.weathero.utils;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public final class LogErrors {

    //Catch exception log if Fabric is initialized
    public static void logException(RuntimeException exception){
        if (Fabric.isInitialized()){
            Crashlytics.logException(exception);
        }
    }

    //Catch throwable log if Fabric is initialized
    public static void logThrowable(Throwable throwable){
        if (Fabric.isInitialized()){
            Crashlytics.logException(throwable);
        }
    }
}
