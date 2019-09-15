
package com.gmail.vdomasapp.weathero.model.locationaddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("Location")
    @Expose
    private Location location;

    public Location getLocation() {
        return location;
    }
}
