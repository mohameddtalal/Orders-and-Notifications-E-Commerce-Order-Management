package com.example.demo.DataBase;

import com.example.demo.Customer.Customer;
import com.example.demo.Notification.NotificationRequest;
import com.example.demo.Notification.NotificationTemplate;
import com.example.demo.Order.CompoundOrder;
import com.example.demo.Order.Order;
import com.example.demo.Order.SimpleOrder;
import com.example.demo.Product.Product;

import java.util.*;
import java.util.stream.Collectors;

public class MemoryDB implements IDB {
    public static final IDB instance = new MemoryDB();

    private MemoryDB() {}

    public static IDB getInstance() {
        return instance;
    }

    private final List<Customer> customers = new ArrayList<>();
    private final List<Order> orders = new ArrayList<>();
    private final List<Product> products = new ArrayList<>();
    private final Map<String, Integer> categoryCounts = new HashMap<>();

    private final List<NotificationRequest> notificationRequests = new ArrayList<>();

    private final Queue<NotificationTemplate> notificationQueue = new LinkedList<>();

    @Override
    public Queue<NotificationTemplate> GetNotificationQueue(){
        return notificationQueue;
    }

    @Override
    public void addToQueue(NotificationTemplate notificationTemplate) {
        notificationQueue.add(notificationTemplate);
    }


    @Override
    public void addToNotification(NotificationRequest notificationRequest) {
        notificationRequests.add(notificationRequest);
    }

    @Override
    public List<NotificationRequest> getNotificationList() {
        return notificationRequests;
    }


    @Override
    public List<Customer> getCustomers() {
        return customers;
    }
    @Override
    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public List<CompoundOrder> getCompoundOrders() {
        List<CompoundOrder> orders = new ArrayList<>();
        for(Order order : this.orders){
            if(order instanceof CompoundOrder){
                orders.add((CompoundOrder) order);
            }
        }
        return orders;
    }

    @Override
    public List<SimpleOrder> getSimpleOrders() {
        List<SimpleOrder> orders = new ArrayList<>();
        for(Order order : this.orders){
            if(order instanceof SimpleOrder){
                orders.add((SimpleOrder) order);
            }
        }
        return orders;
    }
    @Override
    public Optional<Customer> findCustomerByEmail(String email) {
        return customers.stream().filter(c -> c.getEmail().equals(email)).findFirst();
    }

    @Override
    public void saveCustomer(Customer customer) {
        customers.add(customer);
    }
    @Override
    public List<Product> getProducts() {
        return products;
    }
    @Override
    public Optional<Product> getProductById(String serialNumber) {
        return products.stream().filter(p -> p.getSerialNumber().equals(serialNumber)).findFirst();
    }

    @Override
    public Optional<Order> getOrderById(String orderId) {
        return orders.stream().filter(o -> o.getOrderId().equals(orderId)).findFirst();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return products.stream().filter(p -> p.getCategory().equals(category)).collect(Collectors.toList());
    }

    @Override
    public void addProduct(Product product) {
        products.add(product);
        categoryCounts.put(product.getCategory(), categoryCounts.getOrDefault(product.getCategory(), 0) + product.getRemainingParts());
    }

    @Override
    public void addOrder(Order order) {
        orders.add(order);
    }


    @Override
    public List<Product> getProductsByIds(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (Product product : this.products) {
            if (productIds.contains(product.getSerialNumber())) {
                products.add(product);
            }
        }
        return products;
    }

    @Override
    public List<Product> findProductsByName(List<String> productNames) {
        List<Product> selectedProducts = new ArrayList<>();
        for (String productName : productNames) {
            Optional<Product> productOptional = findProductByName(productName);
            Product selectedProduct = productOptional.orElse(null);
            if (selectedProduct != null) {
                selectedProducts.add(selectedProduct);
            } else {
                System.out.println("Product not found. Please enter a valid product name.");
            }
        }
        return selectedProducts;
    }

    @Override
    public Optional<Product> findProductByName(String name) {
        return products.stream()
                .filter(product -> product.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public void removeProduct(String serialNumber) {
        Optional<Product> productToRemove = getProductById(serialNumber);
        if (productToRemove.isPresent()) {
            products.remove(productToRemove.get());
            String category = productToRemove.get().getCategory();
            categoryCounts.put(category, categoryCounts.get(category) - productToRemove.get().getRemainingParts());
        }
    }

    @Override
    public Map<String, Integer> getCategoryCounts() {
        return categoryCounts;
    }
}
