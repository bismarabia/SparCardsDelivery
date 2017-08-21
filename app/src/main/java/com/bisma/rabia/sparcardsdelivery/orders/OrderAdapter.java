package com.bisma.rabia.sparcardsdelivery.orders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bisma.rabia.sparcardsdelivery.R;
import com.bisma.rabia.sparcardsdelivery.model.request.Params;
import com.bisma.rabia.sparcardsdelivery.model.request.User;
import com.bisma.rabia.sparcardsdelivery.model.request.UserClient;
import com.bisma.rabia.sparcardsdelivery.model.response.cards.Card;
import com.bisma.rabia.sparcardsdelivery.model.response.cards.GetCards;
import com.bisma.rabia.sparcardsdelivery.model.response.masetCards.GetMasterBarCodes;
import com.bisma.rabia.sparcardsdelivery.model.response.masetCards.MasterBarCode;
import com.bisma.rabia.sparcardsdelivery.scan.ScanItems;
import com.bisma.rabia.sparcardsdelivery.model.response.connect.Order;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.FoodViewHolder> {

    private static List<Order> ordersList;
    private Context context;

    private List<Card> cardsList;
    private List<MasterBarCode> masterCardsList;

    private SharedPreferences prefDataConnect;


    static class FoodViewHolder extends RecyclerView.ViewHolder {

        CardView order_cv;
        TextView orderName, orderQuantity, orderStartDate, orderFinishDate, orderCategory;
        ImageView task_status;

        FoodViewHolder(final View itemView) {
            super(itemView);
            order_cv = (CardView) itemView.findViewById(R.id.order_cv);
            orderName = (TextView) itemView.findViewById(R.id.order_name);
            orderQuantity = (TextView) itemView.findViewById(R.id.order_quantity);
            orderStartDate = (TextView) itemView.findViewById(R.id.order_start_time);
            orderFinishDate = (TextView) itemView.findViewById(R.id.order_finish_time);
            orderCategory = (TextView) itemView.findViewById(R.id.order_category);
            task_status = (ImageView) itemView.findViewById(R.id.task_status);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.getContext().startActivity(new Intent(view.getContext(), ScanItems.class));
                }
            });
        }


    }

    OrderAdapter(List<Order> orders, Context context) {
        OrderAdapter.ordersList = orders;
        this.context = context;
        prefDataConnect = context.getSharedPreferences("login_pref", Context.MODE_PRIVATE);

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new FoodViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final FoodViewHolder foodViewHolder, int position) {
        foodViewHolder.orderName.setText(ordersList.get(position).getName());
        foodViewHolder.orderQuantity.setText("Quantity: " + ordersList.get(position).getQuantity());
        foodViewHolder.orderCategory.setText(ordersList.get(position).getCategory() + "€");
        foodViewHolder.orderStartDate.setText("Starting time:  " + ordersList.get(position).getStart());
        foodViewHolder.orderFinishDate.setText("Ending time: " + ordersList.get(position).getEnd());

        if (ordersList.get(position).getCompleted() == 0) {
            foodViewHolder.task_status.setImageResource(R.drawable.in_progress1);
        } else
            foodViewHolder.task_status.setImageResource(R.drawable.order_done);

        foodViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCards(String.valueOf(ordersList.get(foodViewHolder.getAdapterPosition()).getId()));
                getMasterCards(String.valueOf(ordersList.get(foodViewHolder.getAdapterPosition()).getId()));
                foodViewHolder.itemView.getContext().startActivity(new Intent(
                        foodViewHolder.itemView.getContext(), ScanItems.class));
            }
        });
    }


    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    private UserClient getClient() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor login = new HttpLoggingInterceptor();
        login.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        okHttpClientBuilder.addInterceptor(login);

        Retrofit.Builder builder = new Retrofit.Builder().
                baseUrl("http://spar.identiks.webfactional.com/").
                addConverterFactory(GsonConverterFactory.create()).
                client(okHttpClientBuilder.build());

        Retrofit retrofit = builder.build();
        return retrofit.create(UserClient.class);
    }

    private void getCards(String id) {
        User user = new User(new Params(id));
        UserClient client = getClient();

        Call<GetCards> call = client.getCards(user);
        call.enqueue(new Callback<GetCards>() {
            @Override
            public void onResponse(Call<GetCards> call, Response<GetCards> response) {
                if (response.body() != null) {
                    cardsList = response.body().getCards();
                    Gson gson = new Gson();
                    SharedPreferences.Editor editor = prefDataConnect.edit();
                    editor.putString("cards_list", gson.toJson(cardsList));
                    editor.apply();
                }
            }

            @Override
            public void onFailure(Call<GetCards> call, Throwable t) {

            }
        });


    }

    private void getMasterCards(String id) {

        User user = new User(new Params(id));
        UserClient client = getClient();

        Call<GetMasterBarCodes> call = client.getMasterCards(user);
        call.enqueue(new Callback<GetMasterBarCodes>() {
            @Override
            public void onResponse(Call<GetMasterBarCodes> call, Response<GetMasterBarCodes> response) {
                if (response.body() != null) {
                    masterCardsList = response.body().getMasterBarCodes();
                    Gson gson = new Gson();
                    SharedPreferences.Editor editor = prefDataConnect.edit();
                    editor.putString("master_cards_list", gson.toJson(masterCardsList));
                    editor.apply();
                }

            }

            @Override
            public void onFailure(Call<GetMasterBarCodes> call, Throwable t) {

            }

        });

    }

}
