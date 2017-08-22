package com.bisma.rabia.sparcardsdelivery.model.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Params {
    String username, password, auth, idd;

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("EAN_for_mass_activation")
    @Expose
    private String eANForMassActivation;
    @SerializedName("cards")
    @Expose
    List<CardToSet> cardToSets;

    public Params(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Params(String id) {
        this.idd = id;
    }

    public Params(int id, String EAN_masterCode, List<CardToSet> cardToSets) {
        this.id = id;
        this.eANForMassActivation = EAN_masterCode;
        this.cardToSets = cardToSets;
    }
}
