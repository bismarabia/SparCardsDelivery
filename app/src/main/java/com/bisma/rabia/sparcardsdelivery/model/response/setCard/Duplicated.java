package com.bisma.rabia.sparcardsdelivery.model.response.setCard;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Duplicated {

    @SerializedName("cards")
    @Expose
    private String[] cards = null;
    @SerializedName("package")
    @Expose
    private String[] _package = null;

    public String[] getCards() {
        return cards;
    }

    public void setCards(String[] cards) {
        this.cards = cards;
    }

    public String[] get_package() {
        return _package;
    }

    public void set_package(String[] _package) {
        this._package = _package;
    }
}