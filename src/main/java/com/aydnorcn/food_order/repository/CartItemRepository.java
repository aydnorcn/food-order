package com.aydnorcn.food_order.repository;

import com.aydnorcn.food_order.entity.Cart;
import com.aydnorcn.food_order.entity.CartItem;
import com.aydnorcn.food_order.entity.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Page<CartItem> findByCart(Cart cart, Pageable pageable);

    Optional<CartItem> findByCartAndFood(Cart cart, Food food);
    List<CartItem> findAllByCart(Cart cart);
    void deleteAllByCart(Cart cart);
}
