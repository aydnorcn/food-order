package com.aydnorcn.food_order.dto.coupon;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateCouponRequestDto {

    private String code;

    @NotNull(message = "Discount percentage is required")
    @DecimalMin(value = "1", message = "Discount percentage must be greater than or equal to 1")
    @DecimalMax(value = "100", message = "Discount percentage must be less than or equal to 100")
    private Double discountPercentage;

    @NotNull(message = "Minimum amount is required")
    @DecimalMin(value = "1", message = "Minimum amount must be greater than or equal to 1")
    private Double minimumAmount;

    @NotNull(message = "Expire date is required")
    @Future(message = "Expire date must be in the future")
    private LocalDate expireDate;

    @NotNull(message = "Remaining usages is required")
    @DecimalMin(value = "1", message = "Remaining usages must be greater than or equal to 1")
    private Integer remainingUsages;

    @NotNull(message = "Active is required")
    private Boolean active;
}