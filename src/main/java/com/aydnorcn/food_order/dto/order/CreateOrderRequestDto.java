package com.aydnorcn.food_order.dto.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateOrderRequestDto {

    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", message = "Invalid coupon format")
    private String couponCode;

    private String note;

    @NotNull(message = "Address id is required")
    private Long addressId;
}