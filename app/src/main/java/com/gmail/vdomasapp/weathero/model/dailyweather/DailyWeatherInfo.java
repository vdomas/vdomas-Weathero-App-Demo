package com.gmail.vdomasapp.weathero.model.dailyweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DailyWeatherInfo {

    @SerializedName("dt")
    @Expose
    private Integer dt;

    @SerializedName("temp")
    @Expose
    private DailyWeatherTemperature dailyWeatherTemperature;

    @SerializedName("weather")
    @Expose
    private List<DailyWeatherIcon> dailyWeatherIconList = null;

    @SerializedName("speed")
    @Expose
    private Double speed;

    @SerializedName("deg")
    @Expose
    private Double deg;

    @SerializedName("clouds")
    @Expose
    private Integer clouds;

    @SerializedName("rain")
    @Expose
    private Double rain;

    public Integer getDt() {
        return dt;
    }

    public DailyWeatherTemperature getDailyWeatherTemperature() {
        return dailyWeatherTemperature;
    }

    public List<DailyWeatherIcon> getDailyWeatherIconList() {
        return dailyWeatherIconList;
    }

    public Double getSpeed() {
        return speed;
    }

    public int getSpeedInt(){
        return (int)Math.round(getSpeed());
    }

    public Double getDeg() {
        return deg;
    }

    public int getDegInt(){
        return (int)Math.round(getDeg());
    }

    public Integer getClouds() {
        return clouds;
    }

    public Double getRain() {
        return rain;
    }

    public String getFormattedDay(){
        Date date = new Date();
        date.setTime((long)getDt()*1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("E", Locale.UK);
        return dateFormat.format(date);
    }
}
