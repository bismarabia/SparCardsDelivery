package com.bisma.rabia.sparcardsdelivery.model.request;


import com.bisma.rabia.sparcardsdelivery.model.response.cards.GetCards;
import com.bisma.rabia.sparcardsdelivery.model.response.connect.ConnectGetOrder;
import com.bisma.rabia.sparcardsdelivery.model.response.masetCards.GetMasterBarCodes;
import com.bisma.rabia.sparcardsdelivery.model.response.setCard.SetCards;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserClient {

    @POST("/connect/")
    Call<ConnectGetOrder> loginUser(@Body User user);

    @POST("/getMasterCodes/")
    Call<GetMasterBarCodes> getMasterCards(@Body User user);

    @POST("/getCardToSets/")
    Call<GetCards> getCards(@Body User user);

    @POST("/setCardToSets/")
    Call<SetCards> setCards(@Body User user);


}

