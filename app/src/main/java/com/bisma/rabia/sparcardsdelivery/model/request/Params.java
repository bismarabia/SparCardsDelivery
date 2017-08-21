package com.bisma.rabia.sparcardsdelivery.model.request;


import com.bisma.rabia.sparcardsdelivery.model.response.setCard.CardToSet;

import java.util.List;

public class Params {
    String username, password, auth, id, EAN_masterCode;
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
        this.EAN_masterCode = EAN_masterCode;
        this.cardToSets = cardToSets;
    }
}
