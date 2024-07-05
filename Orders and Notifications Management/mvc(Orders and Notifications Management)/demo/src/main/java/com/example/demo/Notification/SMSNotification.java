package com.example.demo.Notification;

import com.example.demo.Order.CompoundOrder;
import com.example.demo.Order.Order;
import com.example.demo.Order.SimpleOrder;

public class SMSNotification extends Notification {

    @Override
    public void sendNotification(NotificationRequest request) {
        Order order = request.getOrder();
        if (order instanceof SimpleOrder) {
            SimpleOrder simpleOrder = (SimpleOrder) order; // Safe cast
            System.out.println(request.getTemplate().getChannels() + " sent to " + simpleOrder.getCustomer().getPhoneNumber() + ": Your order is confirmed.");
        } else if (order instanceof CompoundOrder) {
            CompoundOrder compoundOrder = (CompoundOrder) order; // Safe cast
            System.out.println(request.getTemplate().getChannels() + " sent to " + compoundOrder.getOrders().get(0).getCustomer().getPhoneNumber() + ": Your order is confirmed.");

        } else {
            throw new UnsupportedOperationException("Notification not supported for this order type");
        }
    }
}