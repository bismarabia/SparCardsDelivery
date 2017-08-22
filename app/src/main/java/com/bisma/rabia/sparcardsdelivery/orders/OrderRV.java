package com.bisma.rabia.sparcardsdelivery.orders;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.bisma.rabia.sparcardsdelivery.R;
import com.bisma.rabia.sparcardsdelivery.login.LoginActivity;
import com.bisma.rabia.sparcardsdelivery.model.response.connect.Order;
import com.bisma.rabia.sparcardsdelivery.scan.ScanItems;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_sign_out:
                startActivity(new Intent(OrderRV.this, LoginActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
