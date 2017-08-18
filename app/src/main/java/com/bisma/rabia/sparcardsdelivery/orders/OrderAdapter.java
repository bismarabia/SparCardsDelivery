package com.bisma.rabia.sparcardsdelivery.orders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bisma.rabia.sparcardsdelivery.R;
import com.bisma.rabia.sparcardsdelivery.scan.ScanItems;

import java.util.List;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.FoodViewHolder> {

    private static List<Order> purchaseList;
    private Context context;

    static class FoodViewHolder extends RecyclerView.ViewHolder {

        CardView order_cv;
        TextView orderName, orderQuantity, orderStartDate, orderFinishDate, orderCategory;

        FoodViewHolder(final View itemView) {
            super(itemView);
            order_cv = (CardView) itemView.findViewById(R.id.order_cv);
            this.orderName = (TextView) itemView.findViewById(R.id.order_name);
            this.orderQuantity = (TextView) itemView.findViewById(R.id.order_quantity);
            this.orderStartDate = (TextView) itemView.findViewById(R.id.order_start_time);
            this.orderFinishDate = (TextView) itemView.findViewById(R.id.order_finish_time);
            this.orderCategory = (TextView) itemView.findViewById(R.id.order_category);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.getContext().startActivity(new Intent(view.getContext(), ScanItems.class));
                }
            });
        }


    }

    OrderAdapter(List<Order> purchaseList, Context context) {
        OrderAdapter.purchaseList = purchaseList;
        this.context = context;
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
    public void onBindViewHolder(final FoodViewHolder foodViewHolder, final int position) {
        foodViewHolder.orderName.setText(purchaseList.get(position).getOrderName());
        foodViewHolder.orderQuantity.setText("Quantity: " + purchaseList.get(position).getOrderQuantity());
        foodViewHolder.orderCategory.setText(purchaseList.get(position).getOrderCategory());
        foodViewHolder.orderStartDate.setText("Starting time: "+purchaseList.get(position).getOrderStartDate());
        foodViewHolder.orderFinishDate.setText("Ending time: "+purchaseList.get(position).getOrderFinishDate());



        // on click
    }


    @Override
    public int getItemCount() {
        return purchaseList.size();
    }

    public static interface AdapterCallback {

    }
}
