
package com.bisma.rabia.sparcardsdelivery.model.response.cards;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Card {

    @SerializedName("gift_card_EAN")
    @Expose
    private String giftCardEAN;
    @SerializedName("card_bar_code")
    @Expose
    private String cardBarCode;
    @SerializedName("packaging_bar_code")
    @Expose
    private String packagingBarCode;
    @SerializedName("selling_ean")
    @Expose
    private String sellingEan;
    @SerializedName("packaging_EAN")
    @Expose
    private String packagingEAN;
    @SerializedName("card_packaging_relation")
    @Expose
    private String cardPackagingRelation;
    @SerializedName("pin")
    @Expose
    private String pin;

    public String getGiftCardEAN() {
        return giftCardEAN;
    }

    public void setGiftCardEAN(String giftCardEAN) {
        this.giftCardEAN = giftCardEAN;
    }

    public String getCardBarCode() {
        return cardBarCode;
    }

    public void setCardBarCode(String cardBarCode) {
        this.cardBarCode = cardBarCode;
    }

    public String getPackagingBarCode() {
        return packagingBarCode;
    }

    public void setPackagingBarCode(String packagingBarCode) {
        this.packagingBarCode = packagingBarCode;
    }

    public String getSellingEan() {
        return sellingEan;
    }

    public void setSellingEan(String sellingEan) {
        this.sellingEan = sellingEan;
    }

    public String getPackagingEAN() {
        return packagingEAN;
    }

    public void setPackagingEAN(String packagingEAN) {
        this.packagingEAN = packagingEAN;
    }

    public String getCardPackagingRelation() {
        return cardPackagingRelation;
    }

    public void setCardPackagingRelation(String cardPackagingRelation) {
        this.cardPackagingRelation = cardPackagingRelation;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

}
