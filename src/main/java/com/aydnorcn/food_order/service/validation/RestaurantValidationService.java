package com.aydnorcn.food_order.service.validation;

import com.aydnorcn.food_order.dto.restaurant.PatchRestaurantRequestDto;
import com.aydnorcn.food_order.entity.Restaurant;
import com.aydnorcn.food_order.entity.User;
import com.aydnorcn.food_order.exception.NoAuthorityException;
import com.aydnorcn.food_order.exception.ResourceNotFoundException;
import com.aydnorcn.food_order.repository.RestaurantRepository;
import com.aydnorcn.food_order.service.UserContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class RestaurantValidationService {

    private final RestaurantRepository restaurantRepository;
    private final UserContextService userContextService;

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

    public void validatePatchRestaurantDetails(Restaurant restaurant, PatchRestaurantRequestDto dto){
        if(dto.getPhone() != null && !dto.getPhone().equals(restaurant.getPhone())){
            if (restaurantRepository.existsByPhone(dto.getPhone())) {
                throw new ResourceNotFoundException("Phone already exists on database!");
            }
        }

        if(dto.getEmail() != null && !dto.getEmail().equals(restaurant.getEmail())){
            if (restaurantRepository.existsByEmail(dto.getEmail())) {
                throw new ResourceNotFoundException("Email already exists on database!");
            }
        }

        if(dto.getOpenTime() != null && dto.getCloseTime() != null){
            if (LocalTime.parse(dto.getOpenTime()).isAfter(LocalTime.parse(dto.getCloseTime()))) {
                throw new ResourceNotFoundException("Open time should be before close time!");
            }
        }
    }

    public void validateAuthority(Restaurant restaurant, String action) {
        User currentAuthenticatedUser = userContextService.getCurrentAuthenticatedUser();

        if(restaurant.getOwner().getId().equals(currentAuthenticatedUser.getId())){
            return;
        }

        if(userContextService.isCurrentAuthenticatedUserAdmin()){
            return;
        }

        if(userContextService.isCurrentAuthenticatedUserStaff()){
            return;
        }

        throw new NoAuthorityException("User with ID " + currentAuthenticatedUser.getId() + " is not authorized to " + action);
    }
}
