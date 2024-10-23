package com.aydnorcn.food_order.dto.food;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateFoodRequestDto {

    @NotNull(message = "Name should not be null")
    @Size(min = 10, max = 100, message = "Name should be between 10 and 100 characters")
    private String name;

    @NotNull(message = "Description should not be null")
    @Size(min = 10, max = 255, message = "Description should be between 10 and 255 characters")
    private String description;

    @NotNull(message = "Price should not be null")
    @DecimalMin(value = "0.0", message = "Price should be greater than 0")
    private Double price;

    @NotNull(message = "Restaurant ID should not be null")
    private String restaurantId;

    @NotNull(message = "Category ID should not be null")
    private Long categoryId;

    private MultipartFile image;
}
