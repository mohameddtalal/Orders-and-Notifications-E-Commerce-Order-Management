package com.example.demo.DataBase;

import com.example.demo.Customer.Customer;
import com.example.demo.Notification.NotificationRequest;
import com.example.demo.Notification.NotificationTemplate;
import com.example.demo.Order.CompoundOrder;
import com.example.demo.Order.Order;
import com.example.demo.Order.SimpleOrder;
import com.example.demo.Product.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

public interface IDB {
    List<Customer> getCustomers();
    List<Order> getOrders();
    Optional<Customer> findCustomerByEmail(String email);
    void saveCustomer(Customer customer);
    List<Product> getProductsByCategory(String category);
    Optional<Product> getProductById(String serialNumber);
    List<Product> getProducts();
    void addProduct(Product product);
    void removeProduct(String serialNumber);

    Queue<NotificationTemplate> GetNotificationQueue();
    void addToNotification(NotificationRequest notificationRequest);
    List<NotificationRequest> getNotificationList();

    void addToQueue(NotificationTemplate notificationTemplate);
    List<Product> getProductsByIds(List<String> productIds);

    Optional<Order> getOrderById(String orderId);
    List<Product> findProductsByName(List<String> productNames);

     Optional<Product> findProductByName(String name);

    Map<String, Integer> getCategoryCounts();

     List<SimpleOrder> getSimpleOrders();

     List<CompoundOrder> getCompoundOrders();
     void addOrder(Order order);

}
