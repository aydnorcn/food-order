package com.aydnorcn.food_order.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "Email cannot be null or empty!")
    private String email;
    @NotBlank(message = "Password cannot be null or empty!")
    private String password;

    @NotBlank(message = "Firstname cannot be null or empty!")
    private String firstName;
    @NotBlank(message = "Lastname cannot be null or empty!")
    private String lastName;
}
