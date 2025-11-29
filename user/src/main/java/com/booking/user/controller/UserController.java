package com.booking.user.controller;

import com.booking.user.entity.User;
import com.booking.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // Create new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User saved = service.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // Get user details
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        User user = service.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    // Validate if user exists (used by Booking Service)
    @GetMapping("/validate/{userId}")
    public ResponseEntity<Boolean> isValidUser(@PathVariable Long userId) {
        boolean exists = service.isValidUser(userId);
        return ResponseEntity.ok(exists);
    }
}
