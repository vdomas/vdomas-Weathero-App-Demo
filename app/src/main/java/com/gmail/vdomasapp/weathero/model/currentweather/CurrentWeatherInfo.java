package com.gmail.vdomasapp.weathero.model.currentweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentWeatherInfo {

    @SerializedName("temp")
    @Expose
    private Double temp;

    @SerializedName("temp_min")
    @Expose
    private Double tempMin;

    @SerializedName("temp_max")
    @Expose
    private Double tempMax;

    public Double getTemp() {
        return temp;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public int getFormattedTemperature() {
        return (int) Math.round(getTemp());
    }
}
