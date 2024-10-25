package com.aydnorcn.food_order.dto.address;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateAddressRequestDto {

    @NotNull(message = "Full name is required")
    private String fullName;

    @NotNull(message = "Address is required")
    private String address;

    @NotNull(message = "City is required")
    private String city;

    @NotNull(message = "Phone is required")
    private String phone;

    @NotNull(message = "Zip code is required")
    private String zipCode;
}
