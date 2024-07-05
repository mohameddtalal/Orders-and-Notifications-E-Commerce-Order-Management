package com.example.demo.Notification;

import com.example.demo.Order.Order;
import com.example.demo.Order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Queue;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/sendSMS")
    public ResponseEntity<?> sendSMS(@RequestBody OrderIdRequest orderIdRequest) {

        Order order = orderService.getOrder(orderIdRequest.getOrderId());

        if (order == null) {
            return ResponseEntity.badRequest().body("Order not found");
        }

        NotificationRequest request = new NotificationRequest(order) ;

        return notificationService.sendSMS(request);
    }


    @PostMapping("/sendEmail")
    public ResponseEntity<?> sendEmail(@RequestBody OrderIdRequest orderIdRequest) {
        Order order = orderService.getOrder(orderIdRequest.getOrderId());

        if (order == null) {
            return ResponseEntity.badRequest().body("Order not found");
        }

        NotificationRequest request = new NotificationRequest(order) ;

        return notificationService.sendEmail(request);
    }


    @GetMapping("/get")
    public Queue<String> getAllNote (){
        return notificationService.getAllNote();
    }

}