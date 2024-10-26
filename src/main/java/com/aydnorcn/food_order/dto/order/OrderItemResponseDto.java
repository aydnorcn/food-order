package com.aydnorcn.food_order.dto.order;

import com.aydnorcn.food_order.entity.OrderItem;
import lombok.Data;

@Data
public class OrderItemResponseDto {

    private Long id;
    private Long orderId;
    private String name;
    private String description;
    private Double price;
    private String category;
    private String restaurantId;
    private Integer quantity;
    private Double totalPrice;

    public OrderItemResponseDto(OrderItem item) {
        this.id = item.getId();
        this.orderId = item.getOrder().getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.price = item.getPrice();
        this.category = item.getCategory();
        this.restaurantId = item.getRestaurant().getId();
        this.quantity = item.getQuantity();
        this.totalPrice = item.getTotalPrice();
    }
}
