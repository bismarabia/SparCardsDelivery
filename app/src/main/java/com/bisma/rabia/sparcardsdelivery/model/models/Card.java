package com.bisma.rabia.sparcardsdelivery.model.models;


public class Card {
    String position_number, selling_ean,
            packaging_EAN, packaging_bar_code,
            gift_card_EAN, card_bar_code, pin,
            card_packaging_relation;

    public Card(String position_number, String selling_ean, String packaging_EAN, String packaging_bar_code, String gift_card_EAN, String card_bar_code, String pin, String card_packaging_relation) {
        this.position_number = position_number;
        this.selling_ean = selling_ean;
        this.packaging_EAN = packaging_EAN;
        this.packaging_bar_code = packaging_bar_code;
        this.gift_card_EAN = gift_card_EAN;
        this.card_bar_code = card_bar_code;
        this.pin = pin;
        this.card_packaging_relation = card_packaging_relation;
    }
}
