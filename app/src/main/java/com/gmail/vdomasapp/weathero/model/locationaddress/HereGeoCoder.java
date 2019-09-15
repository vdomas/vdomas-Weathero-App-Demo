
package com.gmail.vdomasapp.weathero.model.locationaddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HereGeoCoder {

    @SerializedName("Response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }
}
