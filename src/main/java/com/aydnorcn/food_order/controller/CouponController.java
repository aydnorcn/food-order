package com.aydnorcn.food_order.controller;

import com.aydnorcn.food_order.dto.coupon.CreateCouponRequestDto;
import com.aydnorcn.food_order.entity.Coupon;
import com.aydnorcn.food_order.service.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/{couponId}")
    public Coupon getCouponById(@PathVariable Long couponId){
        return couponService.getCouponById(couponId);
    }

    @GetMapping("/code/{couponCode}")
    public Coupon getCouponByCode(@PathVariable String couponCode){
        return couponService.getCouponByCode(couponCode);
    }

    @PostMapping
    public Coupon createCoupon(@Valid @RequestBody CreateCouponRequestDto dto){
        return couponService.createCoupon(dto);
    }

    @PatchMapping("/{couponId}")
    public Coupon patchCoupon(@PathVariable Long couponId, @Valid @RequestBody CreateCouponRequestDto dto){
        return couponService.patchCoupon(couponId, dto);
    }

    @DeleteMapping("/{couponId}")
    public void deleteCoupon(@PathVariable Long couponId){
        couponService.deleteCoupon(couponId);
    }
}