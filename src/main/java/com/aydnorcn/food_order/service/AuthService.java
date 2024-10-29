package com.aydnorcn.food_order.service;

import com.aydnorcn.food_order.dto.auth.LoginRequest;
import com.aydnorcn.food_order.dto.auth.LoginResponse;
import com.aydnorcn.food_order.dto.auth.RegisterRequest;
import com.aydnorcn.food_order.dto.auth.RegisterResponse;
import com.aydnorcn.food_order.entity.Cart;
import com.aydnorcn.food_order.entity.Role;
import com.aydnorcn.food_order.entity.User;
import com.aydnorcn.food_order.exception.AlreadyExistsException;
import com.aydnorcn.food_order.jwt.JwtTokenProvider;
import com.aydnorcn.food_order.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;

    public LoginResponse login(LoginRequest request) {
        Authentication authentication;
        try{
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (BadCredentialsException e){
            log.warn("Unsuccessful login attempt | Email: {}", request.getEmail());
            throw new BadCredentialsException("Invalid email or password");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        log.info("User successfully logged in | Email: {}", request.getEmail());
        return new LoginResponse(request.getEmail(), token);
    }

    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            log.error("Email already exists on database: {}", request.getEmail());
            throw new AlreadyExistsException("Email already exists on database!");
        }

        Role role = roleService.getRoleByName("user");
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        User user = new User();

        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCart(new Cart());

        user.setRoles(roles);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        userRepository.save(user);

        log.info("User successfully registered | First name: {}, Last name: {}, email: {}", user.getFirstName(), user.getLastName(), user.getEmail());
        return new RegisterResponse(request.getEmail(), request.getFirstName(), request.getLastName());
    }
}
