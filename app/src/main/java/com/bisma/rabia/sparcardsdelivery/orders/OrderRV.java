package com.bisma.rabia.sparcardsdelivery.orders;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bisma.rabia.sparcardsdelivery.R;

import java.util.ArrayList;
import java.util.List;

public class OrderRV extends AppCompatActivity {

    List<Order> orderList = new ArrayList<>();
    RecyclerView rvOrder;
    OrderAdapter orderAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_rv);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("ORDERS");

        orderList.add(new Order("Z1", "2000", "01/08/2017", "01/09/2017", "10€"));
        orderList.add(new Order("Z2", "5000", "11/08/2017", "03/09/2017", "30€"));
        orderList.add(new Order("Z3", "3000", "29/09/2017", "21/09/2017", "100€"));
        orderList.add(new Order("Z1", "2000", "01/08/2017", "01/09/2017", "10€"));
        orderList.add(new Order("Z2", "5000", "11/08/2017", "03/09/2017", "30€"));
        orderList.add(new Order("Z3", "3000", "29/09/2017", "21/09/2017", "100€"));

        orderAdapter = new OrderAdapter(orderList, OrderRV.this);
        rvOrder = (RecyclerView) findViewById(R.id.order_rv);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(OrderRV.this);
        rvOrder.setLayoutManager(layoutManager);
        rvOrder.setAdapter(orderAdapter);


    }
}
