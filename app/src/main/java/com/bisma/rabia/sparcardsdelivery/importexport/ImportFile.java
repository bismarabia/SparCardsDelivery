package com.bisma.rabia.sparcardsdelivery.importexport;


public class ImportFile {

    int position_number, item_number, selling_amount, mass_activation_serials_amount, surplus_serial_amount, surplus_mass_activation_serials_amount;
    String selling_EAN, purchase_EAN, packaging_EAN, mass_activation_EAN, item_description;

    // for master barcode
    public ImportFile(int position_number, String mass_activation_EAN, int mass_activation_serials_amount, int surplus_mass_activation_serials_amount, String purchase_EAN) {
        this.position_number = position_number;
        this.purchase_EAN = purchase_EAN;
        this.mass_activation_EAN = mass_activation_EAN;
        this.mass_activation_serials_amount = mass_activation_serials_amount;
        this.surplus_mass_activation_serials_amount = surplus_mass_activation_serials_amount;
    }

    // for other cards

}
