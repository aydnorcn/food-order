package com.aydnorcn.food_order.dto.category;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCategoryRequestDto {

    @NotNull(message = "Name is required!")
    @Size(min = 3, max = 15, message = "Name must be between 3 and 50 characters!")
    private String name;
}