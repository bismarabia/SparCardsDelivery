
package com.bisma.rabia.sparcardsdelivery.model.response.cards;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCards {

    @SerializedName("result")
    @Expose
    private Result result;

    @SerializedName("cards")
    @Expose
    private List<Card> cards = null;

    @SerializedName("error")
    @Expose
    private String error;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
