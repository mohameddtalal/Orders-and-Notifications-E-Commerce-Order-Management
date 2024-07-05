package com.example.demo.Order;

import java.util.ArrayList;
import java.util.List;

public class CompoundOrder extends Order {
    private List<SimpleOrder> orders;

    private double shippingFees;

    public CompoundOrder(String id,List<SimpleOrder> orders ,double shippingFees) {
        super(id, shippingFees);
        this.orders = orders;
        this.shippingFees = shippingFees;
    }

    public void setId(String id){
        orderId= id;
    }
    public void addOrder(SimpleOrder order) {
            orders.add(order);
    }

    public void removeOrder(SimpleOrder order) {
        orders.remove(order);
    }

    public double getShippingFees() {
        return shippingFees;
    }

    public void setShippingFees(double shippingFees) {
        this.shippingFees = shippingFees;
    }

    public List<SimpleOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<SimpleOrder> orders) {
        this.orders = orders;
    }

    @Override
    public List<String> getProductNames() {
        List<String> list = new ArrayList<>();
        for (Order order : orders) {
            list.addAll(order.getProductNames());
        }
        return list;
    }

   @Override
    public void placeOrder() {
        for (Order order : orders) {
            order.placeOrder();
        }
    }

    @Override
    public void shipOrder() {
        for (Order order : orders) {
            if (order instanceof SimpleOrder) {
                SimpleOrder simpleOrder = (SimpleOrder) order;
                simpleOrder.getCustomer().setBalance(simpleOrder.getCustomer().getBalance() - shippingFees);
            }
        }
    }
}