package com.aydnorcn.food_order.dto.cart;

import com.aydnorcn.food_order.entity.CartItem;
import lombok.Data;

@Data
public class CartItemResponseDto {

    private Long id;
    private String cartId;
    private Long foodId;
    private String foodName;
    private Integer quantity;
    private Double price;
    private Double totalPrice;

    public CartItemResponseDto(CartItem item) {
        this.id = item.getId();
        this.cartId = item.getCart().getId();
        this.foodId = item.getFood().getId();
        this.foodName = item.getFood().getName();
        this.quantity = item.getQuantity();
        this.price = item.getFood().getPrice();
        this.totalPrice = item.getFood().getPrice() * item.getQuantity();
    }
}