package com.aydnorcn.food_order.dto.review;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateReviewRequestDto {

    @NotNull(message = "Review comment is required")
    private String comment;

    @NotNull(message = "Review rating is required")
    @DecimalMin(value = "1.0", message = "Review rating must be between 1 and 5")
    @DecimalMax(value = "5.0", message = "Review rating must be between 1 and 5")
    private int rating;
}