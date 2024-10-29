package com.aydnorcn.food_order.service.validation;

import com.aydnorcn.food_order.entity.User;
import com.aydnorcn.food_order.exception.NoAuthorityException;
import com.aydnorcn.food_order.service.UserContextService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderValidationService {

    private final UserContextService userContextService;


    public void validateAuthority(User user){
        User currentUser = userContextService.getCurrentAuthenticatedUser();

        if(!currentUser.getId().equals(user.getId()) &&
                !userContextService.isCurrentAuthenticatedUserAdmin() && !userContextService.isCurrentAuthenticatedUserStaff()){
            log.error("User {} is not authorized to perform this action", currentUser.getId());
            throw new NoAuthorityException("You are not authorized to perform this action");
        }
    }

    public void validateAuthority(){
        if(!userContextService.isCurrentAuthenticatedUserAdmin() && !userContextService.isCurrentAuthenticatedUserStaff()){
            log.error("User {} is not authorized to perform this action", userContextService.getCurrentAuthenticatedUser().getId());
            throw new NoAuthorityException("You are not authorized to perform this action");
        }
    }
}