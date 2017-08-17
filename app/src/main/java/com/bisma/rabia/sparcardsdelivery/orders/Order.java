package com.bisma.rabia.sparcardsdelivery.orders;



public class Order {
    private String orderName, orderQuantity, orderStartDate, orderFinishDate, orderCategory;

    public Order(String orderName, String orderQuantity, String orderStartDate, String orderFinishDate, String orderCategory) {
        this.orderName = orderName;
        this.orderQuantity = orderQuantity;
        this.orderStartDate = orderStartDate;
        this.orderFinishDate = orderFinishDate;
        this.orderCategory = orderCategory;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(String orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getOrderStartDate() {
        return orderStartDate;
    }

    public void setOrderStartDate(String orderStartDate) {
        this.orderStartDate = orderStartDate;
    }

    public String getOrderFinishDate() {
        return orderFinishDate;
    }

    public void setOrderFinishDate(String orderFinishDate) {
        this.orderFinishDate = orderFinishDate;
    }

    public String getOrderCategory() {
        return orderCategory;
    }

    public void setOrderCategory(String orderCategory) {
        this.orderCategory = orderCategory;
    }
}
