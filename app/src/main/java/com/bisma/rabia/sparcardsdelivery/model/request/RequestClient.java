package com.bisma.rabia.sparcardsdelivery.model.request;


import com.bisma.rabia.sparcardsdelivery.model.response.cards.GetCards;
import com.bisma.rabia.sparcardsdelivery.model.response.connect.ConnectGetOrder;
import com.bisma.rabia.sparcardsdelivery.model.response.masetCards.GetMasterBarCodes;
import com.bisma.rabia.sparcardsdelivery.model.response.setCard.SetCards;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RequestClient {

    @POST("/connect/")
    Call<ConnectGetOrder> loginUser(@Body Request request);

    @Headers("Accept:application/json")
    @POST("/getMasterCodes/")
    Call<GetMasterBarCodes> getMasterCards(@Body Request request);

    @Headers("Accept:application/json")
    @POST("/getCards/")
    Call<GetCards> getCards(@Body Request request);

    @Headers("Accept:application/json")
    @POST("/setCards/")
    Call<SetCards> setCards(@Body Request request);


}

