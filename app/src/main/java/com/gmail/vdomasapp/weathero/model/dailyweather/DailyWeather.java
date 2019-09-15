package com.gmail.vdomasapp.weathero.model.dailyweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyWeather {

    @SerializedName("city")
    @Expose
    private DailyWeatherCity city;

    @SerializedName("cod")
    @Expose
    private String cod;

    @SerializedName("list")
    @Expose
    private List<DailyWeatherInfo> dailyWeatherInfoList = null;

    public DailyWeatherCity getCity() {
        return city;
    }

    public String getCod() {
        return cod;
    }

    public List<DailyWeatherInfo> getDailyWeatherInfoList() {
        return dailyWeatherInfoList;
    }
}
