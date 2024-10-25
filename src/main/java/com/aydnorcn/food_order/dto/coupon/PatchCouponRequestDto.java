package com.aydnorcn.food_order.dto.coupon;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatchCouponRequestDto {

    private String code;

    @Size(min = 1, max = 100)
    private Double discountPercentage;

    private Double minimumAmount;

    @Future(message = "Expire date must be in the future")
    private LocalDate expireDate;

    @Size(min = 1, message = "Remaining usages must be greater than 0")
    private Integer remainingUsages;
}
