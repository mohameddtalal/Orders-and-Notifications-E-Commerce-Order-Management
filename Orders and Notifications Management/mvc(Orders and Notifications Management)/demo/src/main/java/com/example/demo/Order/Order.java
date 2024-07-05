package com.example.demo.Order;


import java.util.List;

public abstract class Order {

    protected String orderId;

    protected double shippingFees;


    public Order(String orderId, double shippingFees) {
        this.orderId = orderId;
        this.shippingFees = shippingFees;
    }

    public abstract List<String> getProductNames();
    public abstract void placeOrder();

    public abstract void shipOrder();

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}