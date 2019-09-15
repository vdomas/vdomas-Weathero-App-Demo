
package com.gmail.vdomasapp.weathero.model.locationaddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("Address")
    @Expose
    private Address address;

    public Address getAddress() {
        return address;
    }
}
