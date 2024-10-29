package com.aydnorcn.food_order.service.validation;

import com.aydnorcn.food_order.entity.User;
import com.aydnorcn.food_order.exception.NoAuthorityException;
import com.aydnorcn.food_order.service.UserContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteValidationService {

    private final UserContextService userContextService;

    public void validateAuthority(User user, String action) {
        User currentUser = userContextService.getCurrentAuthenticatedUser();
        if (!currentUser.getId().equals(user.getId()) && !userContextService.isCurrentAuthenticatedUserAdmin() && !userContextService.isCurrentAuthenticatedUserStaff()) {
            throw new NoAuthorityException("User with ID " + user.getId() + " is not authorized to " + action);
        }
    }
}