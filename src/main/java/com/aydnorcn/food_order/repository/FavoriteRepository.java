package com.aydnorcn.food_order.repository;

import com.aydnorcn.food_order.entity.Favorite;
import com.aydnorcn.food_order.entity.Food;
import com.aydnorcn.food_order.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Page<Favorite> findAllByUser(User user, Pageable pageable);
    void deleteAllByUser(User user);
    Boolean existsByUserAndFood(User user, Food food);
    Optional<Favorite> findByUserAndFood(User user, Food food);
}
