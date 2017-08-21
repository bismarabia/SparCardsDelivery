package com.bisma.rabia.sparcardsdelivery.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bisma.rabia.sparcardsdelivery.R;
import com.bisma.rabia.sparcardsdelivery.model.request.Params;
import com.bisma.rabia.sparcardsdelivery.model.request.User;
import com.bisma.rabia.sparcardsdelivery.model.request.UserClient;
import com.bisma.rabia.sparcardsdelivery.model.response.connect.ConnectGetOrder;
import com.bisma.rabia.sparcardsdelivery.model.response.connect.Order;
import com.bisma.rabia.sparcardsdelivery.orders.OrderRV;
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

    SharedPreferences prefDataConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefDataConnect = getSharedPreferences("login_pref", Context.MODE_PRIVATE);


        username_tv = (TextView) findViewById(R.id.username);
        password_tv = (TextView) findViewById(R.id.password);

        findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = username_tv.getText().toString();
                password = password_tv.getText().toString();
                connect(username, password);
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

        User user = new User(new Params(username, password));

        Retrofit retrofit = builder.build();
        UserClient client = retrofit.create(UserClient.class);
        Call<ConnectGetOrder> call = client.loginUser(user);
        call.enqueue(new Callback<ConnectGetOrder>() {

            @Override
            public void onResponse(Call<ConnectGetOrder> call, Response<ConnectGetOrder> response) {
                if (response.body() != null) {
                    ordersList = response.body().getOrders();
                    Gson gson = new Gson();
                    SharedPreferences.Editor editor = prefDataConnect.edit();
                    editor.putString("username", username);
                    editor.putString("orders_list", gson.toJson(ordersList));
                    editor.apply();

                    startActivity(new Intent(getApplicationContext(), OrderRV.class));
                } else
                    Toast.makeText(LoginActivity.this, "response body is null", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ConnectGetOrder> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "failed to connect", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
