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

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private String category;

    private Integer quantity;
    private Double totalPrice;

    public OrderItem(String name, String description, Double price, String imageUrl, String category, Integer quantity, Double totalPrice) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
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
        this.restaurant = food.getRestaurant();
        this.quantity = quantity;
        this.totalPrice = food.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", order=" + order.toString() +
                ", restaurant=" + restaurant.toString() +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", category='" + category + '\'' +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                '}';
    }
}