package com.example.demo.Notification;

public class EmailNotificationFactory extends NotificationFactory {
    @Override
    public Notification createNotification(NotificationRequest request) {
        return new EmailNotification();
    }
}