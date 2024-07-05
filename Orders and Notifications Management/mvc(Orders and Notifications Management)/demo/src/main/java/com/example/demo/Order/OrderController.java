package com.example.demo.Order;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private  OrderService orderService;

    @PostMapping("/createSimpleOrder")
    public ResponseEntity<?> createSimpleOrder(@RequestBody SimpleOrder order) {
        return orderService.createSimpleOrder(order);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getOrdersList());
    }

    @PostMapping("/createCompoundOrder")
    public ResponseEntity<?> createCompoundOrder(@RequestBody CompoundOrder order) {
        return orderService.createCompoundOrder(order);

    }

    @GetMapping("/getID")
    public ResponseEntity<List<String>> getAllIds() {
        return ResponseEntity.ok(orderService.getOrdersIds());
    }

}
