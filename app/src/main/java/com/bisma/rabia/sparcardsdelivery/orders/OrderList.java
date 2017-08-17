package com.bisma.rabia.sparcardsdelivery.orders;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bisma.rabia.sparcardsdelivery.R;

import java.util.ArrayList;
import java.util.List;

public class OrderList extends AppCompatActivity {

    List<Order> orderList = new ArrayList<>();
    static RecyclerView recyclerViewPH;
    OrderAdapter orderAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_rv);

//        orderList = RVCart.getPurchaseHistList();
//        purchaseAdapter = new PurchaseAdapter(purchaseHistoryList, PurchaseHistoryRV.this, this);
//        recyclerViewPH = (RecyclerView) findViewById(R.id.purchase_rv);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(PurchaseHistoryRV.this);
//        recyclerViewPH.setLayoutManager(layoutManager);
//        recyclerViewPH.setAdapter(purchaseAdapter);
    }
}
