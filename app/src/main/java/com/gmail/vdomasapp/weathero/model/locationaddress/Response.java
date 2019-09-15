
package com.gmail.vdomasapp.weathero.model.locationaddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {

    @SerializedName("View")
    @Expose
    private List<View> view = null;

    public List<View> getView() {
        return view;
    }
}
