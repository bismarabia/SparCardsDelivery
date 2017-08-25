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
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bisma.rabia.sparcardsdelivery.R;
import com.bisma.rabia.sparcardsdelivery.login.LoginActivity;
import com.bisma.rabia.sparcardsdelivery.model.response.connect.Order;
import com.bisma.rabia.sparcardsdelivery.scan.ScanItems;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rscja.deviceapi.Barcode1D;
import com.rscja.deviceapi.exception.ConfigurationException;
import com.zebra.adc.decoder.Barcode2DWithSoft;

import java.util.ArrayList;
import java.util.List;

public class OrderRV extends AppCompatActivity {

    List<Order> orderList;
    RecyclerView rvOrder;
    OrderAdapter orderAdapter;

    SharedPreferences prefDataConnect;

    private Barcode1D mInstance;
    public Barcode2DWithSoft scanner2D;

    static boolean isBarcode1D = false, isBarcode2D = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean finish = getIntent().getBooleanExtra("finish", false);
        if (finish) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.order_rv);

        try {
            mInstance = Barcode1D.getInstance();
        } catch (ConfigurationException e) {
            Log.i("1D exp == ", e.getMessage());
        }
        isBarcode1D = mInstance.open();
        Log.i("orderRV isBarcode1D == ", "" + isBarcode1D);

        try {
            scanner2D = Barcode2DWithSoft.getInstance();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        isBarcode2D = scanner2D.open(OrderRV.this);
        Log.i("orderRV isBarcode2D == ", "" + isBarcode2D);
        if (!isBarcode2D) {
            scanner2D.stopPreview();
            scanner2D.stopScan();
            scanner2D.close();
            scanner2D.disableAllCodeTypes();
            scanner2D = null;
            try {
                mInstance = Barcode1D.getInstance();
            } catch (ConfigurationException e) {
                e.printStackTrace();
            }
            isBarcode1D = mInstance.open();

        }

        prefDataConnect = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefDataConnect.edit();
        editor.putBoolean("isBarcode1D", isBarcode1D);
        editor.putBoolean("isBarcode2D", isBarcode2D);
        editor.apply();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("ORDERS");

        if (!prefDataConnect.contains("orders_list"))
            orderList = new ArrayList<>();
        else {
            Gson gson = new Gson();
            orderList = gson.fromJson(prefDataConnect.getString("orders_list", null), new TypeToken<ArrayList<Order>>() {
            }.getType());
            editor = prefDataConnect.edit();
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
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == 139))
            if ((event.getRepeatCount() == 0)) {
                String b = mInstance.scan();
                Log.i("1D **** == ", b);
                return true;
            }

        return super.onKeyDown(keyCode, event);
    }
}
