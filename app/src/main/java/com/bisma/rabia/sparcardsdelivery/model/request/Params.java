package com.bisma.rabia.sparcardsdelivery.model.request;



import java.util.List;

public class Params {
    String username, password, auth;

    String token;

    public Params(String username, String password, String auth) {
        this.username = username;
        this.password = password;
        this.auth = auth;
    }

    public Params(String token) {
        this.token = token;
    }

    public Params(String token, List<Participant> participants) {
        this.token = token;
    }
}
