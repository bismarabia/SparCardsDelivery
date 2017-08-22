package com.bisma.rabia.sparcardsdelivery.scan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bisma.rabia.sparcardsdelivery.R;
import com.bisma.rabia.sparcardsdelivery.login.LoginActivity;
import com.bisma.rabia.sparcardsdelivery.model.request.Params;
import com.bisma.rabia.sparcardsdelivery.model.request.Request;
import com.bisma.rabia.sparcardsdelivery.model.request.RequestClient;
import com.bisma.rabia.sparcardsdelivery.model.response.cards.Card;
import com.bisma.rabia.sparcardsdelivery.model.response.masetCards.MasterBarCode;
import com.bisma.rabia.sparcardsdelivery.model.request.CardToSet;
import com.bisma.rabia.sparcardsdelivery.model.response.setCard.SetCards;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rscja.deviceapi.Barcode1D;
import com.rscja.deviceapi.exception.ConfigurationException;
import com.zebra.adc.decoder.Barcode2DWithSoft;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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
    String username, barcodeCard, barcodePackaging = "";

    int order_id, box_quantity, counter = 0, cardScannedCounter = 0;

    List<Card> cardsList = new ArrayList<>();
    List<CardToSet> cardsScannedList = new ArrayList<>();
    List<MasterBarCode> masterCardsList = new ArrayList<>();
    HashMap<String, String> cardMap = new HashMap<>(), sellingEANMap = new HashMap<>();

    static boolean isBarcode1D = false, isBarcode2D = false;
    public Barcode2DWithSoft scanner2D;
    private boolean threadStop = true;
    private Thread thread;

    private SharedPreferences prefDataConnect;

    private SoundPool soundPool;
    private int soundIDGreenFlag, soundIDRedFlag;
    private Barcode1D mInstance;
    private String barCode1D;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_items);

        prefDataConnect = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        username = prefDataConnect.getString("username", "user_name");
        box_quantity = prefDataConnect.getInt("order_box_quantity", 50);
        order_id = prefDataConnect.getInt("order_id", 1);
        Log.i("box_quantity == ", "" + box_quantity);
        Log.i("order_id == ", "" + order_id);

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

        try {
            mInstance = Barcode1D.getInstance();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        isBarcode1D = mInstance.open();

        try {
            scanner2D = Barcode2DWithSoft.getInstance();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        isBarcode2D = scanner2D.open(ScanItems.this);

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
                clear();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(3)
                    .setAudioAttributes(audioAttributes)
                    .build();
            soundIDGreenFlag = soundPool.load(this, R.raw.barcodebeep, 1);
            soundIDRedFlag = soundPool.load(this, R.raw.serror, 1);
        } else {
            soundPool = new SoundPool(10, 3, 5);
            soundIDGreenFlag = soundPool.load(this, R.raw.barcodebeep, 1);
            soundIDRedFlag = soundPool.load(this, R.raw.serror, 1);
        }
    }

    private void clear() {
        no_cards.setText("0");
        msg_status.setText("Scan The Package First");
        status.setText("");
        barcode.setText("");
        scan_btn.setText("SCAN");
        threadStop = true;
    }

    private void scan() {

        if (isBarcode2D) {
            Log.i("2D Scanner == ", "will be used");
            if (scanner2D != null)
                scanner2D.setScanCallback(mScanCallback);

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
                boolean result;
                if (scanner2D != null) {
                    result = scanner2D.open(ScanItems.this);
                    if (result) {
                        scanner2D.setParameter(324, 1);
                        scanner2D.setParameter(300, 0); // Snapshot Aiming
                        scanner2D.setParameter(361, 0); // Image Capture Illumination
                    }
                }
            }
        } else {
            Log.i("1D Scanner == ", "will be used");
            barCode1D = mInstance.scan();
            updateUI(barCode1D);
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
            barCode = barCode.trim();
            barcode.setText(barCode);

            msg_status.setTextColor(Color.rgb(220, 0, 7));      // color == red
            msg_status.setText("Scan The Package First");
            status.setText("");

            /*
            *   counter == 0  >> Nothing is scanned, package is to be scanned first
            *   counter == 1  >> Package is scanned, and the card has to be scanned next, when Card is scanned,
            *                       if found & match counter = 0 and if cardScannedCounter == box_quantity
            *                       counter = 2 and move to scanning masterCode; otherwise counter = 0
            *   counter == 2  >>  **** package and card are scanned properly, so masterCode will be scanned,
            *                       if MasterCode scanned exists, call setCards() and counter = 0, otherwise
            *                       counter = 2
            */

            updateUI(barCode);

        }
    };

    void updateUI(String barCode) {
        if (counter == 0) {
            msg_status.setText("Package Scanned...Scan The Card");
            barcodePackaging = barCode;
            counter++;
            status.setText("");
        } else if (counter == 1) {
            barcodeCard = barCode;

            Log.i("scanned Package == ", barcodePackaging);
            Log.i("scanned Card == ", barcodeCard);

            if (barcodeCard.equals(cardMap.get(barcodePackaging))) {
                CardToSet cardScanned = new CardToSet(
                        username,
                        new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Calendar.getInstance().getTime()),
                        sellingEANMap.get(barcodePackaging),
                        barcodePackaging,
                        cardMap.get(barcodePackaging),
                        1);
                cardsScannedList.add(cardScanned);
                cardScannedCounter++;
                counter = 0;
                Log.i("status == ", "Perfect, found match");
                int count = Integer.valueOf(no_cards.getText().toString());         // increment card scanned count
                no_cards.setText(String.valueOf((++count)));                        // set the count to textView

                status.setTextColor(Color.rgb(30, 204, 0));             // color == green
                status.setText("Perfect");
                soundPool.play(soundIDGreenFlag, 0.9f, 0.9f, 1, 0, 1);
                // change 2 it to box_quantity
                if (cardScannedCounter == 2) {                          // all cards along their package are scanned in one box
                    counter = 2;                                        // counter = 2 so we can scan master code
                    msg_status.setTextColor(Color.rgb(17, 187, 255));   // color == blue
                    msg_status.setText("Scan the MasterCode");
                } else {
                    msg_status.setTextColor(Color.rgb(220, 0, 7));          // color == red
                    msg_status.setText("Scan another Package..");
                }
            } else {
                Log.i("status == ", "Package's and Card's barcode are a mismatch..");
                status.setTextColor(Color.rgb(220, 0, 7));      // color == red
                status.setText("Mismatch");
                counter = 0;
                soundPool.play(soundIDRedFlag, 0.9f, 0.9f, 1, 0, 1);
            }


        } else if (counter == 2) {  // package's and card's code scanned properly, now it's time to scan master code
            for (int j = 0; j < masterCardsList.size(); j++) {
                if (masterCardsList.get(j).getMassActivationEAN().equals(barCode)) {    // masterCode found
                    Log.i("status == ", "Perfect, Master found match");
                    Log.i("cardScanned size == ", "" + cardsScannedList.size());
                    setCards(new Request(new Params(order_id, masterCardsList.get(j).getMassActivationEAN(), cardsScannedList)));
                    counter = 0;
                    cardScannedCounter = 0;
                    soundPool.play(soundIDGreenFlag, 0.9f, 0.9f, 1, 0, 1);

                    clear();
                    scanner2D.close();
                    clear_btn.setEnabled(true);
                    boolean result;
                    if (scanner2D != null) {
                        result = scanner2D.open(ScanItems.this);
                        if (result) {
                            scanner2D.setParameter(324, 1);
                            scanner2D.setParameter(300, 0); // Snapshot Aiming
                            scanner2D.setParameter(361, 0); // Image Capture Illumination
                        }
                    }
                }
            }
            if (counter == 2) {      // The masterCode scanned doesn't exist
                status.setTextColor(Color.rgb(220, 0, 7));          // color == red
                status.setText("Master Code Not Found!!");
                msg_status.setTextColor(Color.rgb(220, 0, 7));      // color == red
                msg_status.setText("Find another valid MasterCode..");
                soundPool.play(soundIDRedFlag, 0.9f, 0.9f, 1, 0, 1);
                // counter will stay 2 so we can start scanning other master code
            }
        }
    }

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

            Gson gson = new Gson();
            if (prefDataConnect.contains("cards_list")) {
                while (cardsList.size() == 0)
                    cardsList = gson.fromJson(prefDataConnect.getString("cards_list", null), new TypeToken<ArrayList<Card>>() {
                    }.getType());
            }
            if (prefDataConnect.contains("master_cards_list")) {
                while (masterCardsList.size() == 0)
                    masterCardsList = gson.fromJson(prefDataConnect.getString("master_cards_list", null), new TypeToken<ArrayList<MasterBarCode>>() {
                    }.getType());
            }
            for (int i = 0; i < cardsList.size(); i++) {
                cardMap.put(cardsList.get(i).getPackagingEAN(), cardsList.get(i).getGiftCardEAN());
                sellingEANMap.put(cardsList.get(i).getPackagingEAN(), cardsList.get(i).getSellingEan());
            }
            Log.i("post card ms list ==", "size : " + masterCardsList.size());
            Log.i("post card list ==", "size : " + cardsList.size());
            Log.i("post hashMap ==", "size : " + cardMap.size());
            Log.i("post sellingEANMap ==", "size : " + sellingEANMap.size());
            SharedPreferences.Editor editor = prefDataConnect.edit();
            editor.apply();

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

    @Override
    protected void onPause() {
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
        super.onResume();

        if (scanner2D != null) {
            new InitTask().execute();
        }

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
                startActivity(new Intent(ScanItems.this, LoginActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == 139))
            if ((event.getRepeatCount() == 0)) {
                scan();
                return true;
            }

        return super.onKeyDown(keyCode, event);
    }

    void setCards(Request request) {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor login = new HttpLoggingInterceptor();
        login.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClientBuilder.addInterceptor(login);

        Retrofit.Builder builder = new Retrofit.Builder().
                baseUrl("http://spar.identiks.webfactional.com/").
                addConverterFactory(GsonConverterFactory.create()).
                client(okHttpClientBuilder.build());

        Retrofit retrofit = builder.build();
        RequestClient client = retrofit.create(RequestClient.class);
        Call<SetCards> call = client.setCards(request);
        call.enqueue(new Callback<SetCards>() {
            @Override
            public void onResponse(Call<SetCards> call, Response<SetCards> response) {
                if (response.body() != null) {
                    Log.i("response msg == ", response.body().getResult().getMsg());
                    cardsScannedList.clear();
                } else {
                    Log.i("response.body() is == ", "null");
                }
            }

            @Override
            public void onFailure(Call<SetCards> call, Throwable t) {
                Log.i("sorry == ", "failed to Set");

            }
        });
    }

}
