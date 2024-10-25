package com.aydnorcn.food_order.dto.coupon;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateCouponRequestDto {

    private String code;

    @NotNull(message = "Discount percentage is required")
    @Size(min = 1, max = 100)
    private Double discountPercentage;

    @NotNull(message = "Minimum amount is required")
    private Double minimumAmount;

    @NotNull(message = "Expire date is required")
    @Future(message = "Expire date must be in the future")
    private LocalDate expireDate;

    @NotNull(message = "Remaining usages is required")
    @Size(min = 1, message = "Remaining usages must be greater than 0")
    private Integer remainingUsages;

    @NotNull(message = "Active is required")
    private Boolean active;
}