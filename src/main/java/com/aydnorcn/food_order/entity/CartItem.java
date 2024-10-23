package com.aydnorcn.food_order.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;

    private Integer quantity;

    public CartItem(Cart cart, Food food, Integer quantity) {
        this.cart = cart;
        this.food = food;
        this.quantity = quantity;
    }
}
