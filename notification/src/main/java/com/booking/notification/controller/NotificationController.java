package com.booking.notification.controller;

import com.booking.notification.dto.BookingResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @PostMapping
    public ResponseEntity<String> sendNotification(@RequestBody BookingResponse response) {
        System.out.println("Notification sent: " + response);
        return ResponseEntity.ok("Notification sent successfully");
    }
}
