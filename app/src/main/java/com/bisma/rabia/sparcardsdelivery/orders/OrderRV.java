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
import com.bisma.rabia.sparcardsdelivery.model.response.connect.ConnectGetOrder;
import com.bisma.rabia.sparcardsdelivery.model.response.connect.Order;
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
            Toast.makeText(getApplicationContext(), "size After " + orderList.size(), Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = prefDataConnect.edit();
            editor.apply();
        }

        //        orderList.add(new Order("Z1", "2000", "01/08/2017", "01/09/2017", "10€"));
//        orderList.add(new Order("Z2", "5000", "11/08/2017", "03/09/2017", "30€"));
//        orderList.add(new Order("Z3", "3000", "29/09/2017", "21/09/2017", "100€"));
//        orderList.add(new Order("Z1", "2000", "01/08/2017", "01/09/2017", "10€"));
//        orderList.add(new Order("Z2", "5000", "11/08/2017", "03/09/2017", "30€"));
//        orderList.add(new Order("Z3", "3000", "29/09/2017", "21/09/2017", "100€"));

        orderAdapter = new OrderAdapter(orderList, OrderRV.this);
        rvOrder = (RecyclerView) findViewById(R.id.order_rv);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(OrderRV.this);
        rvOrder.setLayoutManager(layoutManager);
        rvOrder.setAdapter(orderAdapter);


    }

    void getOrders() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor login = new HttpLoggingInterceptor();
        login.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClientBuilder.addInterceptor(login);

        Retrofit.Builder builder = new Retrofit.Builder().
                baseUrl("https://staging.idconference.eu/").
                addConverterFactory(GsonConverterFactory.create()).
                client(okHttpClientBuilder.build());

        User user = new User("connect-method", "connect", new Params("un", "psw", "auth"));

        Retrofit retrofit = builder.build();
        UserClient client = retrofit.create(UserClient.class);
        Call<ConnectGetOrder> call = client.loginUser(user);
        call.enqueue(new Callback<ConnectGetOrder>() {

            @Override
            public void onResponse(Call<ConnectGetOrder> call, Response<ConnectGetOrder> response) {
                if (response.body() != null) {

                } else {

                }
            }

            @Override
            public void onFailure(Call<ConnectGetOrder> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "failed to connect", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
