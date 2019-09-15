package com.gmail.vdomasapp.weathero.model.dailyweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyWeatherCity {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("country")
    @Expose
    private String country;

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }
}
