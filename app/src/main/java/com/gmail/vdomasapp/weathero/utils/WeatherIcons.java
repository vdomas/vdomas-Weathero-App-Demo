package com.gmail.vdomasapp.weathero.utils;

import android.util.ArrayMap;

import com.gmail.vdomasapp.weathero.R;

import java.util.Collections;
import java.util.Map;

public final class WeatherIcons {

    public static Map weatherIconCollection(){

        ArrayMap<String, Integer> iconMap = new ArrayMap<>();
        iconMap.put("01d", R.drawable.ic_day_sunny);
        iconMap.put("02d",R.drawable.ic_day_cloudy);
        iconMap.put("03d",R.drawable.ic_cloud);
        iconMap.put("04d",R.drawable.ic_cloudy);
        iconMap.put("09d",R.drawable.ic_day_showers);
        iconMap.put("10d",R.drawable.ic_day_rain);
        iconMap.put("11d",R.drawable.ic_thunderstorm);
        iconMap.put("13d",R.drawable.ic_snow);
        iconMap.put("50d",R.drawable.ic_fog);

        iconMap.put("01n",R.drawable.ic_night_clear);
        iconMap.put("02n",R.drawable.ic_night_alt_cloudy);
        iconMap.put("03n",R.drawable.ic_cloud);
        iconMap.put("04n",R.drawable.ic_cloudy);
        iconMap.put("09n",R.drawable.ic_night_alt_showers);
        iconMap.put("10n",R.drawable.ic_night_alt_rain);
        iconMap.put("11n",R.drawable.ic_thunderstorm);
        iconMap.put("13n",R.drawable.ic_snow);
        iconMap.put("50n",R.drawable.ic_fog);

        return Collections.unmodifiableMap(iconMap);
    }
}
