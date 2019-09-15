package com.gmail.vdomasapp.weathero.model.currentweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentWeatherIcon {

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("icon")
    @Expose
    private String icon;

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
