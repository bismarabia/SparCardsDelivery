package com.bisma.rabia.sparcardsdelivery.scan;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bisma.rabia.sparcardsdelivery.R;
import com.zebra.adc.decoder.Barcode2DWithSoft;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ScanItems extends AppCompatActivity {

    TextView no_cards, status;
    EditText barcode;
    Button scan_btn, clear_btn;

    List<String> barcodeList = new ArrayList<>(),
            barcodeListSoFar = new ArrayList<>(),
            masterCodeList = new ArrayList<>();

    public Barcode2DWithSoft scanner2D;
    ExecutorService executor;
    private boolean threadStop = true;
    private Thread thread;


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
        barcode = (EditText) findViewById(R.id.barcode_et);

        try {
            scanner2D = Barcode2DWithSoft.getInstance();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        scan_btn = (Button) findViewById(R.id.scan_btn);
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scan();
            }
        });

        clear_btn = (Button) findViewById(R.id.clear_btn);
        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                no_cards.setText("0");
                status.setText("");
                barcode.setText("");
                scan_btn.setText("SCAN");
                threadStop = true;
            }
        });
    }

    private void scan() {

        if (scanner2D != null) {

            scanner2D.setScanCallback(mScanCallback);
        }

        Log.i("ErDSoftScanFragment", "doDecode() threadStop=" + threadStop);

        if (threadStop) {

            int iBetween;

            scan_btn.setText("STOP");
            threadStop = false;

            iBetween = 1500;

            clear_btn.setEnabled(false);

            thread = new DecodeThread(true, iBetween);
            thread.start();

        } else {
            scan_btn.setText("SCAN");
            scanner2D.close();
            clear_btn.setEnabled(true);
            threadStop = true;
        }

    }

    private class DecodeThread extends Thread {
        private boolean isContinuous;
        private long sleepTime;


        DecodeThread(boolean isContinuous, int sleep) {
            this.isContinuous = isContinuous;
            this.sleepTime = sleep;
        }

        @Override
        public void run() {
            super.run();

            do {

                scanner2D.scan();

                if (isContinuous) {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            } while (isContinuous && !threadStop);
        }

    }

    public Barcode2DWithSoft.ScanCallback mScanCallback = new Barcode2DWithSoft.ScanCallback() {
        @Override
        public void onScanComplete(int i, int length, byte[] data) {

            if (length < 1) {

                Toast.makeText(getApplicationContext(), "Scan Failure!", Toast.LENGTH_SHORT).show();
                return;
            }

            scanner2D.stopScan();

            String barCode = new String(data);

            if (!barcodeListSoFar.contains(barCode)) {
                int count = Integer.valueOf(no_cards.getText().toString());
                no_cards.setText(String.valueOf((++count)));
                barcode.setText(barCode);
                barcodeListSoFar.add(barCode);
            }

        }
    };

    public class InitTask extends AsyncTask<String, Integer, Boolean> {
        ProgressDialog mypDialog;

        @Override
        protected Boolean doInBackground(String... params) {


            boolean result = false;

            if (scanner2D != null) {
                result = scanner2D.open(ScanItems.this);
                if (result) {
                    scanner2D.setParameter(324, 1);
                    scanner2D.setParameter(300, 0); // Snapshot Aiming
                    scanner2D.setParameter(361, 0); // Image Capture Illumination
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            mypDialog.cancel();

            if (scanner2D != null) {
                scanner2D.setScanCallback(mScanCallback);
            }
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            mypDialog = new ProgressDialog(ScanItems.this);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("init...");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.show();
        }

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();


        threadStop = true;
        scan_btn.setText("SCAN");
        clear_btn.setEnabled(true);

        if (scanner2D != null) {
            scanner2D.stopScan();
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        if (scanner2D != null) {
            new InitTask().execute();
        }
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
