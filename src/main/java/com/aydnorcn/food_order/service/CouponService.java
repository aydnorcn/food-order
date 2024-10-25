package com.aydnorcn.food_order.service;

import com.aydnorcn.food_order.dto.coupon.CreateCouponRequestDto;
import com.aydnorcn.food_order.entity.Coupon;
import com.aydnorcn.food_order.exception.ResourceNotFoundException;
import com.aydnorcn.food_order.repository.CouponRepository;
import com.aydnorcn.food_order.service.validation.CouponValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponValidationService couponValidationService;

    public Coupon getCouponById(Long couponId){
        return couponRepository.findById(couponId).orElseThrow(() -> new ResourceNotFoundException("Coupon not found!"));
    }

    public Coupon getCouponByCode(String couponCode){
        return couponRepository.findByCode(couponCode).orElseThrow(() -> new ResourceNotFoundException("Coupon not found!"));
    }

    public Coupon createCoupon(CreateCouponRequestDto dto) {
        couponValidationService.validateAuthority();
        Coupon newCoupon = new Coupon();

        if (dto.getCode() != null) {
            if (couponRepository.existsByCode(dto.getCode())) {
                throw new ResourceNotFoundException("Coupon with this code already exists!");
            }
        }

        newCoupon.setCode((dto.getCode() == null) ? UUID.randomUUID().toString() : dto.getCode());
        newCoupon.setDiscountPercentage(dto.getDiscountPercentage());
        newCoupon.setMinimumAmount(dto.getMinimumAmount());
        newCoupon.setExpireDate(dto.getExpireDate());
        newCoupon.setRemainingUsages(dto.getRemainingUsages());

        return couponRepository.save(newCoupon);
    }

    public Coupon patchCoupon(Long couponId, CreateCouponRequestDto dto){
        Coupon coupon = getCouponById(couponId);

        couponValidationService.validateAuthority();

        if (dto.getCode() != null) {
            if (couponRepository.existsByCode(dto.getCode())) {
                throw new ResourceNotFoundException("Coupon with this code already exists!");
            }
        }

        if(dto.getCode() != null) coupon.setCode(dto.getCode());
        if(dto.getDiscountPercentage() != null) coupon.setDiscountPercentage(dto.getDiscountPercentage());
        if(dto.getMinimumAmount() != null) coupon.setMinimumAmount(dto.getMinimumAmount());
        if(dto.getExpireDate() != null) coupon.setExpireDate(dto.getExpireDate());
        if(dto.getRemainingUsages() != null) coupon.setRemainingUsages(dto.getRemainingUsages());

        return couponRepository.save(coupon);
    }

    public void deleteCoupon(Long couponId){
        Coupon coupon = getCouponById(couponId);

        couponValidationService.validateAuthority();

        couponRepository.delete(coupon);
    }

    protected void useCoupon(Coupon coupon){
        if(coupon.getRemainingUsages() > 0){
            coupon.setRemainingUsages(coupon.getRemainingUsages() - 1);
            couponRepository.save(coupon);
        }

        if(coupon.getRemainingUsages() == 0){
            coupon.setActive(false);
            couponRepository.save(coupon);
        }

    }
}