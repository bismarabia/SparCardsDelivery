package com.bisma.rabia.sparcardsdelivery.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bisma.rabia.sparcardsdelivery.R;
import com.bisma.rabia.sparcardsdelivery.model.request.Params;
import com.bisma.rabia.sparcardsdelivery.model.request.User;
import com.bisma.rabia.sparcardsdelivery.model.request.UserClient;
import com.bisma.rabia.sparcardsdelivery.orders.OrderRV;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username_tv = (TextView) findViewById(R.id.username);
        password_tv = (TextView) findViewById(R.id.password);


        findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = username_tv.getText().toString();
                password = password_tv.getText().toString();
                //connect();
                startActivity(new Intent(getApplicationContext(), OrderRV.class));
            }
        });
    }

    void connect() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor login = new HttpLoggingInterceptor();
        login.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClientBuilder.addInterceptor(login);

        Retrofit.Builder builder = new Retrofit.Builder().
                baseUrl("https://staging.idconference.eu/").
                addConverterFactory(GsonConverterFactory.create()).
                client(okHttpClientBuilder.build());

        User user = new User("connect-method", "connect", new Params(username, password, "auth"));

        Retrofit retrofit = builder.build();
        UserClient client = retrofit.create(UserClient.class);
        Call<Object> call = client.loginUser(user);
        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                startActivity(new Intent(getApplicationContext(), OrderRV.class));
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "failed to connect", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
