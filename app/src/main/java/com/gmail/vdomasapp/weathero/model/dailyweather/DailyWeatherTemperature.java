package com.gmail.vdomasapp.weathero.model.dailyweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyWeatherTemperature {

    @SerializedName("day")
    @Expose
    private Double day;

    @SerializedName("min")
    @Expose
    private Double min;

    @SerializedName("max")
    @Expose
    private Double max;

    @SerializedName("night")
    @Expose
    private Double night;

    @SerializedName("eve")
    @Expose
    private Double eve;

    @SerializedName("morn")
    @Expose
    private Double morn;

    public Double getDay() {
        return day;
    }

    public int getDayInt(){
        return (int)Math.round(getDay()) ;
    }

    public Double getMin() {
        return min;
    }

    public Double getMax() {
        return max;
    }

    public Double getNight() {
        return night;
    }

    public int getNightInt(){
        return (int)Math.round(getNight());
    }

    public Double getEve() {
        return eve;
    }

    public int getEveInt(){
        return (int)Math.round(getEve());
    }

    public Double getMorn() {
        return morn;
    }

    public int getMornInt(){
        return (int)Math.round(getMorn());
    }

    public int getFormattedMaxTemperature(){
        return (int)Math.round(getMax());
    }

    public int getFormattedMinTemperature(){
        return (int)Math.round(getMin());
    }
}
