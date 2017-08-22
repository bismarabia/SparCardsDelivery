package com.bisma.rabia.sparcardsdelivery.model.response.setCard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("cards_duplicated")
    @Expose
    private String[] cards_duplicated;
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String[] getCards_duplicated() {
        return cards_duplicated;
    }

    public void setCards_duplicated(String[] cards_duplicated) {
        this.cards_duplicated = cards_duplicated;
    }
}
