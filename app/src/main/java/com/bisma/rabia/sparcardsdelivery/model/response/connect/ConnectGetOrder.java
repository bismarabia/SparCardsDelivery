
package com.bisma.rabia.sparcardsdelivery.model.response.connect;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConnectGetOrder {

    @SerializedName("orders")
    @Expose
    private List<Order> orders = null;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("result")
    @Expose
    private Result result;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

}
