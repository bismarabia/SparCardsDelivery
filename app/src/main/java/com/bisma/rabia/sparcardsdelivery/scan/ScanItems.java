package com.bisma.rabia.sparcardsdelivery.scan;

import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bisma.rabia.sparcardsdelivery.R;

import java.util.ArrayList;
import java.util.List;


public class ScanItems extends AppCompatActivity {

    TextView no_cards, status, barcode;

    List<String> barcodeList = new ArrayList<>();
    List<String> masterCodeList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_items);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("SCAN ITEMS");
        }

        no_cards = (TextView) findViewById(R.id.no_cards_et);
        status = (TextView) findViewById(R.id.status_et);
        barcode = (TextView) findViewById(R.id.barcode_et);

        findViewById(R.id.scan_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.valueOf(no_cards.getText().toString());
                no_cards.setText(String.valueOf((++count)));
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == 139))
            if ((event.getRepeatCount() == 0)) {
                int count = Integer.valueOf(no_cards.getText().toString());
                no_cards.setText(String.valueOf((++count)));
                return true;
            }

        return super.onKeyDown(keyCode, event);
    }
}
