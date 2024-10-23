package com.aydnorcn.food_order.dto.cart;

import lombok.Data;

@Data
public class CartRequestDto {

    private Long foodId;
    private Integer quantity;
}
