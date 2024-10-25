package com.aydnorcn.food_order.repository;

import com.aydnorcn.food_order.entity.Order;
import com.aydnorcn.food_order.entity.Review;
import com.aydnorcn.food_order.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findAllByUser(User user, Pageable pageable);
    Boolean existsByOrder(Order order);
}
