
package com.bisma.rabia.sparcardsdelivery.model.response.masetCards;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterBarCode {

    @SerializedName("mass_activation_EAN")
    @Expose
    private String massActivationEAN;
    @SerializedName("bar_code")
    @Expose
    private String barCode;
    @SerializedName("purchase_EAN")
    @Expose
    private String purchaseEAN;
    @SerializedName("amount")
    @Expose
    private String amount;

    public String getMassActivationEAN() {
        return massActivationEAN;
    }

    public void setMassActivationEAN(String massActivationEAN) {
        this.massActivationEAN = massActivationEAN;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getPurchaseEAN() {
        return purchaseEAN;
    }

    public void setPurchaseEAN(String purchaseEAN) {
        this.purchaseEAN = purchaseEAN;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
