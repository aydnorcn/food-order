package com.aydnorcn.food_order.service.validation;

import com.aydnorcn.food_order.entity.User;
import com.aydnorcn.food_order.exception.NoAuthorityException;
import com.aydnorcn.food_order.service.UserContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewValidationService {

    private final UserContextService userContextService;

    public void validateAuthority(User user){
        User currentUser = userContextService.getCurrentAuthenticatedUser();

        if(!currentUser.getId().equals(user.getId()) &&
                !userContextService.isCurrentAuthenticatedUserAdmin() && !userContextService.isCurrentAuthenticatedUserStaff()){
            throw new NoAuthorityException("You are not authorized to perform this action");
        }
    }

    public void validateAuthority(){
        if(!userContextService.isCurrentAuthenticatedUserAdmin() && !userContextService.isCurrentAuthenticatedUserStaff()){
            throw new NoAuthorityException("You are not authorized to perform this action");
        }
    }
}