
package com.bisma.rabia.sparcardsdelivery.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardToSet {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("selling_EAN")
    @Expose
    private String selling_EAN;
    @SerializedName("EAN_of_packaging")
    @Expose
    private String EAN_of_packaging;
    @SerializedName("card_bar_code")
    @Expose
    private String cardBarCode;
    @SerializedName("card_status")
    @Expose
    private Integer cardStatus;

    public CardToSet(String username, String time, String sellingEAN, String eANOfPackaging, String cardBarCode, Integer cardStatus) {
        this.username = username;
        this.time = time;
        this.selling_EAN = sellingEAN;
        this.EAN_of_packaging = eANOfPackaging;
        this.cardBarCode = cardBarCode;
        this.cardStatus = cardStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSelling_EAN() {
        return selling_EAN;
    }

    public void setSelling_EAN(String selling_EAN) {
        this.selling_EAN = selling_EAN;
    }

    public String getEANOfPackaging() {
        return EAN_of_packaging;
    }

    public void setEANOfPackaging(String eANOfPackaging) {
        this.EAN_of_packaging = eANOfPackaging;
    }

    public String getCardBarCode() {
        return cardBarCode;
    }

    public void setCardBarCode(String cardBarCode) {
        this.cardBarCode = cardBarCode;
    }

    public Integer getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(Integer cardStatus) {
        this.cardStatus = cardStatus;
    }

}
