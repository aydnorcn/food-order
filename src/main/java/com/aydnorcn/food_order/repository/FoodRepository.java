package com.aydnorcn.food_order.repository;

import com.aydnorcn.food_order.entity.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food,Long> {

    Page<Food> findAll(Specification<Food> specification, Pageable pageable);
    List<Food> findAllByRestaurantId(String restaurantId);
}
