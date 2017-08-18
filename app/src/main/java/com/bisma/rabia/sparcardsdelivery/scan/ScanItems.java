package com.bisma.rabia.sparcardsdelivery.scan;

import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bisma.rabia.sparcardsdelivery.R;


public class ScanItems extends AppCompatActivity {

    TextView no_cards, status, barcode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_items);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("SCAN ITEMS");

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
}
