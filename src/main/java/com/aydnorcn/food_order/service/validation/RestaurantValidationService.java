package com.aydnorcn.food_order.service.validation;

import com.aydnorcn.food_order.entity.Restaurant;
import com.aydnorcn.food_order.entity.User;
import com.aydnorcn.food_order.exception.ResourceNotFoundException;
import com.aydnorcn.food_order.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantValidationService {

    private final RestaurantRepository restaurantRepository;

    public void validateRestaurantDetails(Restaurant restaurant) {
        if (restaurantRepository.existsByPhone(restaurant.getPhone())) {
            throw new ResourceNotFoundException("Phone already exists on database!");
        }

        if (restaurantRepository.existsByEmail(restaurant.getEmail())) {
            throw new ResourceNotFoundException("Email already exists on database!");
        }

        if (restaurant.getOpenTime().isAfter(restaurant.getCloseTime())) {
            throw new ResourceNotFoundException("Open time should be before close time!");
        }
    }

    public void validateRestaurantOwnership(Restaurant restaurant, User currentAuthenticatedUser) {
        if (!restaurant.getOwner().getId().equals(currentAuthenticatedUser.getId())) {
            throw new ResourceNotFoundException("You are not the owner of this restaurant!");
        }
    }
}
