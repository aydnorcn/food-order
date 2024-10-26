package com.aydnorcn.food_order.repository;

import com.aydnorcn.food_order.entity.Order;
import com.aydnorcn.food_order.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUser(User user);

    Page<Order> findAll(Specification<Order> specification, Pageable pageable);
}
