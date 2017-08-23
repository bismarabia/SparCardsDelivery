package com.bisma.rabia.sparcardsdelivery.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bisma.rabia.sparcardsdelivery.R;
import com.bisma.rabia.sparcardsdelivery.model.request.Params;
import com.bisma.rabia.sparcardsdelivery.model.request.Request;
import com.bisma.rabia.sparcardsdelivery.model.request.RequestClient;
import com.bisma.rabia.sparcardsdelivery.model.response.connect.ConnectGetOrder;
import com.bisma.rabia.sparcardsdelivery.model.response.connect.Order;
import com.bisma.rabia.sparcardsdelivery.orders.OrderRV;
import com.bisma.rabia.sparcardsdelivery.scan.ScanItems;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    TextView username_tv, password_tv;

    String username, password;
    private List<Order> ordersList;
    Boolean isConnected;

    SharedPreferences prefDataConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        isConnected = isNetworkAvailable();

        prefDataConnect = getSharedPreferences("login_pref", Context.MODE_PRIVATE);


        username_tv = (TextView) findViewById(R.id.username);
        password_tv = (TextView) findViewById(R.id.password);

        findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected) {
                    username = username_tv.getText().toString();
                    password = password_tv.getText().toString();
                    connect(username, password);
                } else {
                    showLocationDialog();
                }
            }
        });
    }

    void connect(final String username, String password) {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor login = new HttpLoggingInterceptor();
        login.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClientBuilder.addInterceptor(login);

        Retrofit.Builder builder = new Retrofit.Builder().
                baseUrl("http://spar.identiks.webfactional.com/").
                addConverterFactory(GsonConverterFactory.create()).
                client(okHttpClientBuilder.build());

        Request request = new Request(new Params(username, password));

        Retrofit retrofit = builder.build();
        RequestClient client = retrofit.create(RequestClient.class);
        Call<ConnectGetOrder> call = client.loginUser(request);
        call.enqueue(new Callback<ConnectGetOrder>() {

            @Override
            public void onResponse(Call<ConnectGetOrder> call, Response<ConnectGetOrder> response) {
                if (response.body() != null) {
                    if (!response.body().getResult().getMsg().equals("not ql")) {
                        ordersList = response.body().getOrders();
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = prefDataConnect.edit();
                        editor.putString("username", username);
                        editor.putString("orders_list", gson.toJson(ordersList));
                        editor.apply();

                        startActivity(new Intent(getApplicationContext(), OrderRV.class));
                        finish();
                    } else {
                        Snackbar.make(getWindow().getDecorView().getRootView(),
                                "Check your input and Try Again!", Snackbar.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(LoginActivity.this, "response body is null", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ConnectGetOrder> call, Throwable t) {
                Snackbar.make(getWindow().getDecorView().getRootView(),
                        "Failed to Connect..Check your Internet Settings!", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected() && isOnline();
    }

    public Boolean isOnline() {
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            return (returnVal == 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void showLocationDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("NO INTERNET ACCESS!!");
        builder.setMessage("Check your wifi settings...");

        builder.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                });
        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isConnected = isNetworkAvailable();
    }
}
