package com.aydnorcn.food_order.dto.review;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateReviewRequestDto {

    @NotNull(message = "Review comment is required")
    private String comment;

    @NotNull(message = "Review rating is required")
    @Size(min = 1, max = 5, message = "Rating must be between 1 and 5")
    private int rating;
}