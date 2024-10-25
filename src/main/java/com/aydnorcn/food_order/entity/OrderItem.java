package com.aydnorcn.food_order.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private String category;
    private String restaurant;

    private Integer quantity;
    private Double totalPrice;

    public OrderItem(String name, String description, Double price, String imageUrl, String category, String restaurant, Integer quantity, Double totalPrice) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
        this.restaurant = restaurant;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public OrderItem(Order order, Food food, Integer quantity) {
        this.order = order;
        this.name = food.getName();
        this.description = food.getDescription();
        this.price = food.getPrice();
        this.imageUrl = food.getImageUrl();
        this.category = food.getCategory().getName();
        this.restaurant = food.getRestaurant().getId();
        this.quantity = quantity;
        this.totalPrice = food.getPrice() * quantity;
    }
}