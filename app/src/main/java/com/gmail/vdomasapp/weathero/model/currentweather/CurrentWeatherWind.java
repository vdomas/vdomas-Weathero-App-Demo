package com.gmail.vdomasapp.weathero.model.currentweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentWeatherWind {

    @SerializedName("speed")
    @Expose
    private Double speed;

    @SerializedName("deg")
    @Expose
    private Double deg;

    public Double getSpeed() {
        return speed;
    }

    public Double getDeg() {
        return deg;
    }

    public int getDegInInteger(){
        return (int)Math.round(getDeg());
    }
}
