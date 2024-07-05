package com.example.demo.Notification;

public abstract class NotificationFactory {
    abstract Notification createNotification(NotificationRequest request);
}