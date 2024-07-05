package com.example.demo.Order;

import com.example.demo.Customer.Customer;
import com.example.demo.Product.Product;

import java.util.ArrayList;
import java.util.List;


public class SimpleOrder extends Order {

    public SimpleOrder(String orderId, Customer customer, List<Product> products, double shippinfFees) {
        super(orderId, shippinfFees);
        this.customer = customer;
        this.products = products;
    }
    private Customer customer;
    private List<Product> products;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public List<String> getProductNames() {
        List<String> names= new ArrayList<>();
        for(int i=0 ; i< products.size();i++) {
            names.add(products.get(i).getName());
        }
        return names;
    }

    public String getCustomerEmail() {
        return customer.getEmail();
    }
    public void setId(String id){
        orderId= id;
    }

    public void setShippingFee(double balance){
     shippingFees= balance;
    }
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public void shipOrder() {

        if(this.customer.getBalance()>shippingFees) {
            this.customer.setBalance(this.customer.getBalance() - shippingFees);
        } else {
            throw new RuntimeException("Not enough balance for customer "+ this.customer.getName());
        }
    }

    @Override
    public void placeOrder() {
        if(this.customer.getBalance()>calculateTotalPrice()){
            this.customer.setBalance(this.customer.getBalance() - calculateTotalPrice());

            for (Product p : products) {
                if (p.getRemainingParts() <= 0) {
                    throw new RuntimeException("Error: Product " + p.getName() + " is out of stock.");
                } else {
                    p.updateRemainingParts(1);
                }
            }
        }

        else {
            throw new RuntimeException("Not enough balance for customer "+ this.customer.getName());
        }

    }

    private double calculateTotalPrice() {
       double totalPrice=0;
        for(Product p : products) {
            totalPrice+= p.getPrice();
        }
        return totalPrice;
    }
}