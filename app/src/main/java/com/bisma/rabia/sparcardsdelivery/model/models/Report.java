package com.bisma.rabia.sparcardsdelivery.model.models;

/**
 * Created by BISMA on 20/08/2017.
 */

public class Report {

    int position_serial_number, selling_EAN,
            EAN_of_packaging, card_bar_code,
            card_status, EAN_for_mass_activation;

    public Report(int position_serial_number, int selling_EAN, int EAN_of_packaging, int card_bar_code, int card_status, int EAN_for_mass_activation) {
        this.position_serial_number = position_serial_number;
        this.selling_EAN = selling_EAN;
        this.EAN_of_packaging = EAN_of_packaging;
        this.card_bar_code = card_bar_code;
        this.card_status = card_status;
        this.EAN_for_mass_activation = EAN_for_mass_activation;
    }

}
