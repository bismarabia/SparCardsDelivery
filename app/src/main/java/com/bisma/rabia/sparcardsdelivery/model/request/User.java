package com.bisma.rabia.sparcardsdelivery.model.request;


public class User {

    String id, method, token;
    Params params;


    public User(String id, String method, Params params) {
        this.id = id;
        this.method = method;
        this.params = params;
    }

    public User(String token) {
        this.token = token;
    }

    public User(Params params) {
        this.params = params;
    }
}
