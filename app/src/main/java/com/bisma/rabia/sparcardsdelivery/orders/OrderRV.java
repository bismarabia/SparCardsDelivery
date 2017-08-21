package com.bisma.rabia.sparcardsdelivery.orders;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.bisma.rabia.sparcardsdelivery.R;
import com.bisma.rabia.sparcardsdelivery.model.request.Params;
import com.bisma.rabia.sparcardsdelivery.model.request.User;
import com.bisma.rabia.sparcardsdelivery.model.request.UserClient;
import com.bisma.rabia.sparcardsdelivery.model.response.cards.Card;
import com.bisma.rabia.sparcardsdelivery.model.response.cards.GetCards;
import com.bisma.rabia.sparcardsdelivery.model.response.connect.ConnectGetOrder;
import com.bisma.rabia.sparcardsdelivery.model.response.connect.Order;
import com.bisma.rabia.sparcardsdelivery.model.response.masetCards.GetMasterBarCodes;
import com.bisma.rabia.sparcardsdelivery.model.response.masetCards.MasterBarCode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderRV extends AppCompatActivity {

    List<Order> orderList;
    RecyclerView rvOrder;
    OrderAdapter orderAdapter;

    SharedPreferences prefDataConnect;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_rv);

        prefDataConnect = getSharedPreferences("login_pref", Context.MODE_PRIVATE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("ORDERS");

        if (!prefDataConnect.contains("orders_list"))
            orderList = new ArrayList<>();
        else {
            Gson gson = new Gson();
            orderList = gson.fromJson(prefDataConnect.getString("orders_list", null), new TypeToken<ArrayList<Order>>() {
            }.getType());
            SharedPreferences.Editor editor = prefDataConnect.edit();
            editor.apply();
        }

        orderAdapter = new OrderAdapter(orderList, OrderRV.this);
        rvOrder = (RecyclerView) findViewById(R.id.order_rv);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(OrderRV.this);
        rvOrder.setLayoutManager(layoutManager);
        rvOrder.setAdapter(orderAdapter);

    }

}
