package com.gmail.vdomasapp.weathero.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.gmail.vdomasapp.weathero.WeatheroApp;

public class NetworkState {

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) WeatheroApp.getInstance().getApplicationContext()
                .getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null  && activeNetwork.isConnected();
    }
}
