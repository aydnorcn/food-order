package com.aydnorcn.food_order.repository;

import com.aydnorcn.food_order.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, String> {

    boolean existsByPhone(String phone);
    boolean existsByEmail(String email);
}
