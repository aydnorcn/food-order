package com.aydnorcn.food_order.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "coupons")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private Double discountPercentage;
    private Double minimumAmount;
    private LocalDate expireDate;
    private Integer remainingUsages;
    private Boolean active;
}
