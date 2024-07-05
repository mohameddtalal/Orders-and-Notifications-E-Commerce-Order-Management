package com.example.demo.Customer;

import com.example.demo.DataBase.IDB;
import com.example.demo.DataBase.MemoryDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private final IDB database = MemoryDB.getInstance();

    public List<Customer> getAllCustomers() {
        return database.getCustomers();
    }

    private String generateCustomerId() {
        int nextId = database.getCustomers().size() + 1;
        String formattedId = String.format("%04d", nextId);
        return formattedId;
    }


    public ResponseEntity<?>  createCustomer(Customer customerRequest) {
           createCustomerInternal(customerRequest.getName(), customerRequest.getEmail(), customerRequest.getPassword(),
                   customerRequest.getLocation(), customerRequest.getBalance(), customerRequest.getPhoneNumber());

        return ResponseEntity.ok("Customer created successfully");
    }

    private void createCustomerInternal(String name, String email, String password, String location, double balance, String phoneNumber) {
        boolean flag=true;
        Optional<Customer> existingCustomer = database.findCustomerByEmail(email);
        if (existingCustomer != null && existingCustomer.isPresent()) {
            flag= false;
        }

        if(flag){
            String id = generateCustomerId();

            Customer customer = new Customer(id, name, email, password, location, balance, phoneNumber);
            database.saveCustomer(customer);
        }

    }

    public Optional<Customer> findCustomerByEmailAndPassword(String email, String password) {
        return database.findCustomerByEmail(email)
                .filter(customer -> customer.getPassword().equals(password));
    }

}


