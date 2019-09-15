
package com.gmail.vdomasapp.weathero.model.locationaddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class View {

    @SerializedName("Result")
    @Expose
    private List<Result> result = null;

    public List<Result> getResult() {
        return result;
    }
}
