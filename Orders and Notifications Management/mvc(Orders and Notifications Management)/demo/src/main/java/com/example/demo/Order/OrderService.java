package com.example.demo.Order;

import com.example.demo.Customer.Customer;
import com.example.demo.DataBase.IDB;
import com.example.demo.DataBase.MemoryDB;
import com.example.demo.Product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private final IDB database = MemoryDB.getInstance();

    public String generateOrderId() {
        int nextId = database.getOrders().size() + 1;
        String formattedId = String.format("%07d", nextId);
        return formattedId;
    }

    public static double generateRandomBalance() {
        Random random = new Random();
        double minBalance = 10.0;
        double maxBalance = 100.0;

        double randomBalance = minBalance + (maxBalance - minBalance) * random.nextDouble();

        randomBalance = Math.round(randomBalance * 100.0) / 100.0;

        return randomBalance;
    }

    public ResponseEntity<?> createSimpleOrder(SimpleOrder order) {
        String id = generateOrderId();
        double shippingFee = generateRandomBalance();
        order.setShippingFee(shippingFee);
        order.setId(id);
        Customer selectedCustomer = database.findCustomerByEmail(order.getCustomer().getEmail())
                .orElseThrow(() -> new RuntimeException("Customer not found."));
        if (selectedCustomer == null) {
            System.out.println("Customer not found.");
        }
        else{
            List<Product> selectedProducts = database.findProductsByName(order.getProductNames());
            SimpleOrder simpleOrder = new SimpleOrder(id, selectedCustomer, selectedProducts, shippingFee);
            simpleOrder.placeOrder();
            simpleOrder.shipOrder();
            database.getOrders().add(simpleOrder);
            System.out.println("Simple Order created successfully:");
        }
        return ResponseEntity.ok("Simple Order created successfully");
    }

    public ResponseEntity<?> createCompoundOrder(CompoundOrder compoundOrderRequest) {
        List<String> targetCustomerEmails = compoundOrderRequest.getOrders().stream()
                .map(order -> ((SimpleOrder) order).getCustomerEmail()) // Safe cast to SimpleOrder
                .collect(Collectors.toList());
        createCompoundOrderInternal( targetCustomerEmails ,compoundOrderRequest.getOrders());

        return ResponseEntity.ok("Compound Order created successfully");
    }

    public void createCompoundOrderInternal(List<String> targetCustomerEmails, List<SimpleOrder> orders) {
        String id = generateOrderId();
        double shippingFee = generateRandomBalance();
        for(int i=0;i<orders.size();i++){
            orders.get(i).setId(id);
            orders.get(i).setShippingFee(shippingFee);
        }

        int i = 0;
        for (String targetCustomerEmail : targetCustomerEmails) {
            Customer targetCustomer = database.findCustomerByEmail(targetCustomerEmail)
                    .orElseThrow(() -> new RuntimeException("Target customer not found: " + targetCustomerEmail));

            List<Product> selectedProducts = database.findProductsByName(orders.get(i).getProductNames());

            orders.get(i).setCustomer(targetCustomer);
            orders.get(i).setProducts(selectedProducts);


            i++;
        }

        CompoundOrder compoundOrder = new CompoundOrder(id, orders,shippingFee);
        compoundOrder.placeOrder();
        compoundOrder.shipOrder();
        database.getOrders().add(compoundOrder);

        System.out.println("Compound Order created successfully: "+ id);
    }


    public List<Order> getOrdersList() {
        return database.getOrders();
    }

    public List<String> getOrdersIds() {
        List<String> Ids= new ArrayList<>();
        for(Order o : database.getOrders()){
            Ids.add(o.getOrderId());
        }
        return Ids;
    }

    public Order getOrder(String orderId) {
        Optional<Order> optionalOrder = database.getOrderById(orderId);
        if (optionalOrder.isPresent()) {
            return optionalOrder.get();  // Return the Order object if present
        } else {

            return null;
        }
    }


}
