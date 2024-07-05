package com.example.demo.Notification;

import com.example.demo.Order.Order;

public class NotificationRequest {
    private Order order;
    private NotificationTemplate template;

    public NotificationRequest(Order order, NotificationTemplate template) {
        this.order = order;
        this.template = template;
    }

    public NotificationRequest(Order order) {
        this.order = order;
    }

    public NotificationTemplate getTemplate() {
        return template;
    }

    public void setTemplate(NotificationTemplate template) {
        this.template = template;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}