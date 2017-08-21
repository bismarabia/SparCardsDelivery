
package com.bisma.rabia.sparcardsdelivery.model.response.setCard;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Params {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("EAN_for_mass_activation")
    @Expose
    private String eANForMassActivation;
    @SerializedName("cardToSets")
    @Expose
    private List<CardToSet> cardToSets = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEANForMassActivation() {
        return eANForMassActivation;
    }

    public void setEANForMassActivation(String eANForMassActivation) {
        this.eANForMassActivation = eANForMassActivation;
    }

    public List<CardToSet> getCardToSets() {
        return cardToSets;
    }

    public void setCardToSets(List<CardToSet> cardToSets) {
        this.cardToSets = cardToSets;
    }

}
