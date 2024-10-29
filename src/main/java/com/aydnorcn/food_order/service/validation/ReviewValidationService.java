package com.aydnorcn.food_order.service.validation;

import com.aydnorcn.food_order.entity.Order;
import com.aydnorcn.food_order.entity.User;
import com.aydnorcn.food_order.exception.NoAuthorityException;
import com.aydnorcn.food_order.exception.ResourceNotFoundException;
import com.aydnorcn.food_order.repository.ReviewRepository;
import com.aydnorcn.food_order.service.UserContextService;
import com.aydnorcn.food_order.utils.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewValidationService {

    private final UserContextService userContextService;
    private final ReviewRepository reviewRepository;

    public void validateReviewCreation(Order order){
        if(reviewRepository.existsByOrder(order)) {
            throw new ResourceNotFoundException("Review already exists!");
        }

        if(order.getStatus() != OrderStatus.DELIVERED) {
            throw new ResourceNotFoundException("Order is not delivered yet!");
        }
    }

    public void validateAuthority(User user, String action){
        User currentUser = userContextService.getCurrentAuthenticatedUser();

        if(!currentUser.getId().equals(user.getId()) &&
                !userContextService.isCurrentAuthenticatedUserAdmin() && !userContextService.isCurrentAuthenticatedUserStaff()){
            throw new NoAuthorityException("User with ID " + user.getId() + " is not authorized to " + action);
        }
    }
}