package com.bisma.rabia.sparcardsdelivery.model.models;

/**
 * Created by BISMA on 20/08/2017.
 */

public class Order {

    String token, name, start, end;
    int quantity, category, completed;

    public Order(String token, String name, String start, String end, int quantity, int category, int completed) {
        this.token = token;
        this.name = name;
        this.start = start;
        this.end = end;
        this.quantity = quantity;
        this.category = category;
        this.completed = completed;
    }
}
