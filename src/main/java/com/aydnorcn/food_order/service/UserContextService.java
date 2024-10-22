package com.aydnorcn.food_order.service;

import com.aydnorcn.food_order.entity.Role;
import com.aydnorcn.food_order.entity.User;
import com.aydnorcn.food_order.exception.ResourceNotFoundException;
import com.aydnorcn.food_order.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserContextService {

    private final UserRepository userRepository;

    public User getCurrentAuthenticatedUser() {
        String currentPrincipalEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(currentPrincipalEmail).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    public boolean isCurrentAuthenticatedUserAdmin() {
        return getCurrentAuthenticatedUser().getRoles().stream().map(Role::getName).anyMatch(x -> x.equals("ROLE_ADMIN"));
    }

    public boolean isCurrentAuthenticatedUserStaff(){
        return getCurrentAuthenticatedUser().getRoles().stream().map(Role::getName).anyMatch(x -> x.equals("ROLE_STAFF"));
    }
}