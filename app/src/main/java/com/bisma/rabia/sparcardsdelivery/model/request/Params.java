package com.bisma.rabia.sparcardsdelivery.model.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Params {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("id")
    @Expose
    private String id;

    private String eANForMassActivation;
    List<CardToSet> cardToSets;

    public Params(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Params(String id) {
        this.id = id;
    }

    public Params(String id, String EAN_masterCode, List<CardToSet> cardToSets) {
        this.id = id;
        this.eANForMassActivation = EAN_masterCode;
        this.cardToSets = cardToSets;
    }
}
