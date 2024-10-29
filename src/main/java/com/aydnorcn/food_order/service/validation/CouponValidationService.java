package com.aydnorcn.food_order.service.validation;

import com.aydnorcn.food_order.entity.Coupon;
import com.aydnorcn.food_order.exception.NoAuthorityException;
import com.aydnorcn.food_order.exception.ResourceNotFoundException;
import com.aydnorcn.food_order.service.UserContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponValidationService {

    private final UserContextService userContextService;

    public void validateCoupon(Coupon coupon){
        if(coupon.getRemainingUsages() == 0){
            throw new ResourceNotFoundException("Coupon is usage limit exceeded!");
        }

        if(!coupon.getActive()){
            throw new ResourceNotFoundException("Coupon is not active!");
        }
    }

    public void validateAuthority(String action){
        if(!userContextService.isCurrentAuthenticatedUserAdmin()){
            throw new NoAuthorityException("User with ID " + userContextService.getCurrentAuthenticatedUser().getId() + " is not authorized to " + action);
        }
    }
}