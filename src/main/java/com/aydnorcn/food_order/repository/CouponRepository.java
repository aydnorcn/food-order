package com.aydnorcn.food_order.repository;

import com.aydnorcn.food_order.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    boolean existsByCode(String code);
    Optional<Coupon> findByCode(String code);
}
