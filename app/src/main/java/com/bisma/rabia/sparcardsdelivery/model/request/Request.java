package com.bisma.rabia.sparcardsdelivery.model.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Request {

    @SerializedName("params")
    @Expose
    Params params;

    public Request(Params params) {
        this.params = params;
    }


}
