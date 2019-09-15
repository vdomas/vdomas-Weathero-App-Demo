package com.gmail.vdomasapp.weathero.model.currentweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentWeather {

    @SerializedName("weather")
    @Expose
    private List<CurrentWeatherIcon> currentWeatherIconList = null;

    @SerializedName("main")
    @Expose
    private CurrentWeatherInfo currentWeatherInfo;

    @SerializedName("wind")
    @Expose
    private CurrentWeatherWind currentWeatherWind;

    @SerializedName("name")
    @Expose
    private String name;

    public List<CurrentWeatherIcon> getCurrentWeatherIconList() {
        return currentWeatherIconList;
    }

    public CurrentWeatherInfo getCurrentWeatherInfo() {
        return currentWeatherInfo;
    }

    public CurrentWeatherWind getCurrentWeatherWind() {
        return currentWeatherWind;
    }

    public String getName() {
        return name;
    }

}
