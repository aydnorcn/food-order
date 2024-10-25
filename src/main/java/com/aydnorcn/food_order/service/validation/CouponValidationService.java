package com.aydnorcn.food_order.service.validation;

import com.aydnorcn.food_order.exception.NoAuthorityException;
import com.aydnorcn.food_order.service.UserContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponValidationService {

    private final UserContextService userContextService;

    public void validateAuthority(){
        if(!userContextService.isCurrentAuthenticatedUserAdmin()){
            throw new NoAuthorityException("You do not have authority to perform this operation!");
        }
    }
}