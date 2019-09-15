package com.gmail.vdomasapp.weathero.model.dailyweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyWeatherIcon {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("main")
    @Expose
    private String main;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("icon")
    @Expose
    private String icon;

    public Integer getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
