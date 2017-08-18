package com.bisma.rabia.sparcardsdelivery.orders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bisma.rabia.sparcardsdelivery.R;
import com.bisma.rabia.sparcardsdelivery.scan.ScanItems;

import java.util.List;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.FoodViewHolder> {

    private static List<Order> ordersList;
    private Context context;

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

    OrderAdapter(List<Order> purchaseList, Context context) {
        OrderAdapter.ordersList = purchaseList;
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
        foodViewHolder.orderName.setText(ordersList.get(position).getOrderName());
        foodViewHolder.orderQuantity.setText("Quantity: " + ordersList.get(position).getOrderQuantity());
        foodViewHolder.orderCategory.setText(ordersList.get(position).getOrderCategory());
        foodViewHolder.orderStartDate.setText("Starting time:  " + ordersList.get(position).getOrderStartDate());
        foodViewHolder.orderFinishDate.setText("Ending time: " + ordersList.get(position).getOrderFinishDate());

        if (ordersList.get(position).getOrderName().equals("Z1")) {
            foodViewHolder.task_status.setImageResource(R.drawable.in_progress1);
        }

        // on click
    }


    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public static interface AdapterCallback {

    }
}
