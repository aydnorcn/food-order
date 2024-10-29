package com.aydnorcn.food_order.service;

import com.aydnorcn.food_order.dto.coupon.CreateCouponRequestDto;
import com.aydnorcn.food_order.entity.Coupon;
import com.aydnorcn.food_order.exception.ResourceNotFoundException;
import com.aydnorcn.food_order.repository.CouponRepository;
import com.aydnorcn.food_order.service.validation.CouponValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponValidationService couponValidationService;
    private final UserContextService userContextService;

    public Coupon getCouponById(Long couponId){
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new ResourceNotFoundException("Coupon not found!"));

        couponValidationService.validateAuthority("view a coupon");

        return coupon;
    }

    public Coupon getCouponByCode(String couponCode){
        Coupon coupon = couponRepository.findByCode(couponCode).orElseThrow(() -> new ResourceNotFoundException("Coupon not found!"));

        return coupon;
    }

    public Coupon createCoupon(CreateCouponRequestDto dto) {
        couponValidationService.validateAuthority("create a coupon");
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
        newCoupon.setActive(dto.getActive());
        newCoupon.setRemainingUsages(dto.getRemainingUsages());

        Coupon savedCoupon = couponRepository.save(newCoupon);

        log.info("Coupon with code {} created by user with ID {}", savedCoupon.getCode(), userContextService.getCurrentAuthenticatedUser().getId());

        return savedCoupon;
    }

    public Coupon patchCoupon(Long couponId, CreateCouponRequestDto dto){
        Coupon coupon = getCouponById(couponId);

        couponValidationService.validateAuthority("update a coupon");

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

        log.info("Coupon with code {} updated by user with ID {}", coupon.getCode(), userContextService.getCurrentAuthenticatedUser().getId());

        return couponRepository.save(coupon);
    }

    public void deleteCoupon(Long couponId){
        Coupon coupon = getCouponById(couponId);

        couponValidationService.validateAuthority("delete a coupon");

        couponRepository.delete(coupon);

        log.info("Coupon with code {} deleted by user with ID {}", coupon.getCode(), userContextService.getCurrentAuthenticatedUser().getId());
    }

    protected void useCoupon(Coupon coupon){
        couponValidationService.validateCoupon(coupon);

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