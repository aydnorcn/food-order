package com.aydnorcn.food_order.dto.cart;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartRequestDto {

    @NotNull(message = "Food id is required")
    private Long foodId;

    @NotNull(message = "Quantity is required")
    @DecimalMin(value = "1", message = "Quantity must be greater than 0")
    private Integer quantity;
}
