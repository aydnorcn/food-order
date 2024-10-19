package com.aydnorcn.food_order.dto.restaurant;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class CreateRestaurantRequestDto {

    @NotNull(message = "Name should not be null")
    @Size(min = 10, max = 100, message = "Name should be between 10 and 100 characters")
    private String name;

    @NotNull(message = "Address should not be null")
    @Size(min = 10, max = 255, message = "Address should be between 10 and 255 characters")
    private String address;

    @NotNull(message = "Phone should not be null")
    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{4}$", message = "Phone number is invalid")
    private String phone;

    @NotNull(message = "Email should not be null")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Open time should not be null")
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "Open time should be in HH:mm format")
    private String openTime;

    @NotNull(message = "Close time should not be null")
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "Close time should be in HH:mm format")
    private String closeTime;

    @NotNull(message = "Open days should not be null")
    @Size(min = 1, max = 7, message = "Open days should be between 1 and 7")
    private Set<String> openDays;
}
