package com.bisma.rabia.sparcardsdelivery.model.models;


public class MasterCard {
    String position_number, purchase_EAN,
            mass_activation_EAN, bar_code, amount;

    public MasterCard(String position_number, String purchase_EAN, String mass_activation_EAN, String bar_code, String amount) {
        this.position_number = position_number;
        this.purchase_EAN = purchase_EAN;
        this.mass_activation_EAN = mass_activation_EAN;
        this.bar_code = bar_code;
        this.amount = amount;
    }
}
