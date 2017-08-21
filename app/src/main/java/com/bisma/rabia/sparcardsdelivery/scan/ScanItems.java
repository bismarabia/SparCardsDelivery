package com.bisma.rabia.sparcardsdelivery.scan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import com.bisma.rabia.sparcardsdelivery.login.LoginActivity;
import com.bisma.rabia.sparcardsdelivery.model.request.Params;
import com.bisma.rabia.sparcardsdelivery.model.request.User;
import com.bisma.rabia.sparcardsdelivery.model.request.UserClient;
import com.bisma.rabia.sparcardsdelivery.model.response.cards.Card;
import com.bisma.rabia.sparcardsdelivery.model.response.connect.ConnectGetOrder;
import com.bisma.rabia.sparcardsdelivery.model.response.masetCards.MasterBarCode;
import com.bisma.rabia.sparcardsdelivery.model.response.setCard.CardToSet;
import com.bisma.rabia.sparcardsdelivery.model.response.setCard.SetCards;
import com.bisma.rabia.sparcardsdelivery.orders.OrderRV;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zebra.adc.decoder.Barcode2DWithSoft;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ScanItems extends AppCompatActivity {

    TextView no_cards, status, msg_status;
    EditText barcode;
    Button scan_btn, clear_btn;
    String username;

    List<String> barcodeList = new ArrayList<>(),
            barcodeListSoFar = new ArrayList<>(),
            masterCodeList = new ArrayList<>();

    List<CardToSet> cardsScannedList = new ArrayList<>();

    int counter = 0, cardScannedCounter = 0;

    public Barcode2DWithSoft scanner2D;
    private boolean threadStop = true;
    private Thread thread;

    List<Card> cardsList = new ArrayList<>();
    List<MasterBarCode> masterCardsList = new ArrayList<>();
    private SharedPreferences prefDataConnect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_items);

        prefDataConnect = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        username = prefDataConnect.getString("username", "user_name");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("SCAN ITEMS");
        }

        no_cards = (TextView) findViewById(R.id.no_cards_et);
        msg_status = (TextView) findViewById(R.id.msg_status);
        status = (TextView) findViewById(R.id.status_et);
        barcode = (EditText) findViewById(R.id.barcode_et);

        new getCardsListTask().execute();

        //Toast.makeText(this, "size : " + cardsList.size() + "\nsize ms : " + masterCardsList.size(), Toast.LENGTH_SHORT).show();

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
            barcode.setText(barCode);

            counter++;
            String barcodeCard = "", barcodePackaging;

            if (counter == 1) {
                Toast.makeText(ScanItems.this, "Scan The CardToSet..", Toast.LENGTH_SHORT).show();
                msg_status.setText("Scan The CardToSet..");
                barcodeCard = barCode;
            } else if (counter == 2) {
                barcodePackaging = barCode;
                for (int j = 0; j < cardsList.size(); j++) {
                    if (cardsList.get(j).getPackagingEAN().equals(barcodePackaging))
                        if (cardsList.get(j).getGiftCardEAN().equals(barcodeCard)) {     // if true, card will be counted
                            int count = Integer.valueOf(no_cards.getText().toString());
                            no_cards.setText(String.valueOf((++count)));
                            Toast.makeText(ScanItems.this, "Perfect!!", Toast.LENGTH_SHORT).show();
                            status.setTextColor(Color.rgb(30, 204, 0));
                            status.setText("Perfect");

                            CardToSet cardScanned = new CardToSet(
                                    username,
                                    new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Calendar.getInstance().getTime()),
                                    cardsList.get(j).getSellingEan(),
                                    cardsList.get(j).getPackagingEAN(),
                                    cardsList.get(j).getGiftCardEAN(),
                                    1);
                            cardsScannedList.add(cardScanned);

                            cardScannedCounter++;
                            counter = 0;
                            if (cardScannedCounter == 50) {
                                counter = 3;
                                msg_status.setTextColor(Color.rgb(40, 123, 255));
                                msg_status.setText("Scan the MasterCode");
                            }

                        } else {
                            Toast.makeText(ScanItems.this, "Package's and CardToSet's barcode are " +
                                    " a mismatch..", Toast.LENGTH_SHORT).show();
                            status.setTextColor(Color.rgb(220, 0, 7));
                            status.setText("Mismatch");
                            counter = 0;
                        }
                    else {
                        Toast.makeText(ScanItems.this, "this card doesn't exist!!", Toast.LENGTH_SHORT).show();
                        status.setTextColor(Color.rgb(220, 0, 7));
                        status.setText("CardToSet Not Found!!");
                        counter = 0;
                    }
                }

            } else if (counter == 3) {
                for (int j = 0; j < masterCardsList.size(); j++) {
                    if (masterCardsList.get(j).getMassActivationEAN().equals(barCode)) {
                        setCards(new User(new Params("1", masterCardsList.get(j).getMassActivationEAN(), cardsScannedList)));
                        counter = 0;
                    } else {
                        counter = 3;
                        // I have nooooo idea
                    }
                }
            }

        }
    };

    private class InitTask extends AsyncTask<String, Integer, Boolean> {
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
            super.onPreExecute();

            mypDialog = new ProgressDialog(ScanItems.this);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("init...");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.show();
        }

    }

    private class getCardsListTask extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            Gson gson = new Gson();
            if (prefDataConnect.contains("cards_list")) {
                cardsList = gson.fromJson(prefDataConnect.getString("cards_list", null), new TypeToken<ArrayList<Card>>() {
                }.getType());
            }
            if (prefDataConnect.contains("master_cards_list")) {
                masterCardsList = gson.fromJson(prefDataConnect.getString("master_cards_list", null), new TypeToken<ArrayList<MasterBarCode>>() {
                }.getType());
            }
            Log.e("post card ms list ==", "size : " + masterCardsList.size());
            Log.e("post card list ==", "size : " + cardsList.size());
            SharedPreferences.Editor editor = prefDataConnect.edit();
            editor.apply();

            return true;
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

    void setCards(User user) {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor login = new HttpLoggingInterceptor();
        login.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClientBuilder.addInterceptor(login);

        Retrofit.Builder builder = new Retrofit.Builder().
                baseUrl("http://spar.identiks.webfactional.com/").
                addConverterFactory(GsonConverterFactory.create()).
                client(okHttpClientBuilder.build());

        Retrofit retrofit = builder.build();
        UserClient client = retrofit.create(UserClient.class);
        Call<SetCards> call = client.setCards(user);
        call.enqueue(new Callback<SetCards>() {
            @Override
            public void onResponse(Call<SetCards> call, Response<SetCards> response) {
                if (response.body() != null) {

                } else
                    Toast.makeText(ScanItems.this, "response body is null", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SetCards> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "failed to connect", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
