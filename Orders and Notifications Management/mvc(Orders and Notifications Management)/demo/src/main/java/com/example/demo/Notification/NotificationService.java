package com.example.demo.Notification;

import com.example.demo.Customer.Customer;
import com.example.demo.DataBase.IDB;
import com.example.demo.DataBase.MemoryDB;
import com.example.demo.Order.Order;
import com.example.demo.Order.SimpleOrder;
import com.example.demo.Product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
public class NotificationService {
    @Autowired
    private final IDB database = MemoryDB.getInstance();

    public ResponseEntity<?> sendSMS(NotificationRequest request) {
        Order order = request.getOrder();
        SimpleOrder simpleOrder = (SimpleOrder) order;
        Customer customer = simpleOrder.getCustomer();
        List<Product> products = simpleOrder.getProducts();
        NotificationFactory notificationFactory = new SmsNotificationFactory();
        NotificationType notificationType = NotificationType.SMS;
        List<String> placeholders = new ArrayList<>();
            placeholders.add(customer.getName());

        for(int i=0; i<products.size();i++){
            placeholders.add(database.getProducts().get(i).getName());
        }

        NotificationTemplate template = new NotificationTemplate(placeholders,notificationType);
        request.setTemplate(template);
        Notification notification = new SMSNotification();
        notificationFactory.createNotification(request);
        database.addToNotification(request);
        database.addToQueue(request.getTemplate());
        notification.sendNotification(request);
        return ResponseEntity.ok("Notification created successfully");
    }

    public ResponseEntity<?> sendEmail(NotificationRequest request) {
        Order order = request.getOrder();
        SimpleOrder simpleOrder = (SimpleOrder) order;
        Customer customer = simpleOrder.getCustomer();
        List<Product> products = simpleOrder.getProducts();
        NotificationFactory notificationFactory = new EmailNotificationFactory();
        NotificationType notificationType = NotificationType.EMAIL;
        List<String> placeholders = new ArrayList<>();
        placeholders.add(customer.getName());

        for(int i=0; i<products.size();i++){
            placeholders.add(database.getProducts().get(i).getName());
        }

        NotificationTemplate template = new NotificationTemplate(placeholders,notificationType);
        request.setTemplate(template);
        Notification notification = new EmailNotification();
        notificationFactory.createNotification(request);
        database.addToNotification(request);
        database.addToQueue(request.getTemplate());
        notification.sendNotification(request);
        return ResponseEntity.ok("Notification created successfully");
    }

    public Queue<String> getAllNote() {
        Queue<String> messages = new LinkedList<>();  // Use a Queue for messages
        for (int i = 0; i < database.getNotificationList().size(); i++) {
            String content = database.getNotificationList().get(i).getTemplate().getContent();
            messages.add(content);
        }
        return messages;
    }
}
