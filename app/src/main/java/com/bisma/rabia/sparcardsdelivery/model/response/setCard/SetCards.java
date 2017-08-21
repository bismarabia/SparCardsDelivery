
package com.bisma.rabia.sparcardsdelivery.model.response.setCard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetCards {

    @SerializedName("params")
    @Expose
    private Params params;

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

}
