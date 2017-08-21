package com.bisma.rabia.sparcardsdelivery.model.request;


import com.bisma.rabia.sparcardsdelivery.model.response.connect.ConnectGetOrder;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserClient {

    @POST("/connect/")
    Call<ConnectGetOrder> loginUser(@Body User user);


}

