package com.bisma.rabia.sparcardsdelivery.model.request;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserClient {

    @POST("/connect/")
    Call<Object> loginUser(@Body User user);


}

