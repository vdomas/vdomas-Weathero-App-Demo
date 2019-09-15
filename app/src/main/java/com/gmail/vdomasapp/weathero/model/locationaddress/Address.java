
package com.gmail.vdomasapp.weathero.model.locationaddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {

    @SerializedName("Country")
    @Expose
    private String country;

    @SerializedName("State")
    @Expose
    private String state;

    @SerializedName("County")
    @Expose
    private String county;

    @SerializedName("City")
    @Expose
    private String city;

    @SerializedName("District")
    @Expose
    private String district;

    @SerializedName("Label")
    @Expose
    private String label;

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getCounty() {
        return county;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getLabel() {
        return label;
    }
}
