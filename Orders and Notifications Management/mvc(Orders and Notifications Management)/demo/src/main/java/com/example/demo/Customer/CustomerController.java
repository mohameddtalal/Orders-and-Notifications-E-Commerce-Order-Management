package com.example.demo.Customer;

import com.example.demo.Product.Product;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {

       return customerService.createCustomer(customer);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Customer>> getAllProducts() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/user/check") //FOR LOGIN
    public ResponseEntity<?> checkUser(@RequestParam String email, @RequestParam String password) {
        Optional<Customer> customer = customerService.findCustomerByEmailAndPassword(email, password);
        if (customer.isPresent()) {
            return ResponseEntity.ok(customer.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
