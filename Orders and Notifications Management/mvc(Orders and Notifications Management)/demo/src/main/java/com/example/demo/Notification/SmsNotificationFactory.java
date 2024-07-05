package com.example.demo.Notification;

public class SmsNotificationFactory extends NotificationFactory {
    @Override
    public Notification createNotification(NotificationRequest request) {
        return new SMSNotification();
    }
}