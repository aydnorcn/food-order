package com.aydnorcn.food_order.service;

import com.aydnorcn.food_order.entity.User;
import com.aydnorcn.food_order.exception.ResourceNotFoundException;
import com.aydnorcn.food_order.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
