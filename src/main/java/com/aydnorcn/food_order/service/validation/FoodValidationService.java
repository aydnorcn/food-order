package com.aydnorcn.food_order.service.validation;

import com.aydnorcn.food_order.entity.Restaurant;
import com.aydnorcn.food_order.entity.User;
import com.aydnorcn.food_order.exception.NoAuthorityException;
import com.aydnorcn.food_order.service.UserContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodValidationService {

    private final UserContextService userContextService;

    public void validateAuthority(Restaurant restaurant, String action) {
        User currentUser = userContextService.getCurrentAuthenticatedUser();
        if (restaurant.getOwner().getId().equals(currentUser.getId())) {
            return;
        }

        if (userContextService.isCurrentAuthenticatedUserAdmin()) {
            return;
        }

        if (userContextService.isCurrentAuthenticatedUserStaff()) {
            return;
        }

        throw new NoAuthorityException("User with ID " + currentUser.getId() + " is not authorized to " + action);
    }
}