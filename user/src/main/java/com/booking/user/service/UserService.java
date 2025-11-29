package com.booking.user.service;

import com.booking.user.entity.User;
import com.booking.user.exception.CustomException;
import com.booking.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public boolean isValidUser(Long userId) {
        return repository.findById(userId).isPresent();
    }

    public User saveUser(User user) {
        // Set id to null to let the database auto-generate it
        user.setId(null);
        return repository.save(user);
    }

    public User getUserById(Long userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new CustomException("User not found with id: " + userId));
    }
}
