package com.aydnorcn.food_order.service;

import com.aydnorcn.food_order.dto.PageResponseDto;
import com.aydnorcn.food_order.dto.restaurant.CreateRestaurantRequestDto;
import com.aydnorcn.food_order.dto.restaurant.PatchRestaurantRequestDto;
import com.aydnorcn.food_order.entity.Restaurant;
import com.aydnorcn.food_order.entity.User;
import com.aydnorcn.food_order.exception.ResourceNotFoundException;
import com.aydnorcn.food_order.repository.RestaurantRepository;
import com.aydnorcn.food_order.service.validation.RestaurantValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final AuthService authService;
    private final RestaurantValidationService restaurantValidationService;

    public Restaurant getRestaurantById(String restaurantId) {
        return restaurantRepository.findById(restaurantId).orElseThrow(() -> new ResourceNotFoundException("Restaurant not found!"));
    }

    public PageResponseDto<Restaurant> getRestaurants(int pageNo, int pageSize){
        return new PageResponseDto<>(restaurantRepository.findAll(PageRequest.of(pageNo, pageSize)));
    }

    public Restaurant createRestaurant(CreateRestaurantRequestDto dto){
        User currentAuthenticatedUser = authService.getCurrentAuthenticatedUser();

        Restaurant restaurant = new Restaurant(dto.getName(), dto.getAddress(), dto.getPhone(), dto.getEmail(),
                currentAuthenticatedUser, LocalTime.parse(dto.getOpenTime()), LocalTime.parse(dto.getCloseTime()),
                dto.getOpenDays().stream().map(day -> DayOfWeek.valueOf(day.toUpperCase(Locale.ENGLISH))).collect(Collectors.toList()));

        restaurantValidationService.validateRestaurantDetails(restaurant);

        return restaurantRepository.save(restaurant);
    }

    public Restaurant updateRestaurantById(String restaurantId, CreateRestaurantRequestDto dto) {
        Restaurant restaurant = getRestaurantById(restaurantId);

        restaurantValidationService.validateRestaurantOwnership(restaurant, authService.getCurrentAuthenticatedUser());

        restaurant.setName(dto.getName());
        restaurant.setAddress(dto.getAddress());
        restaurant.setPhone(dto.getPhone());
        restaurant.setEmail(dto.getEmail());
        restaurant.setOpenTime(LocalTime.parse(dto.getOpenTime()));
        restaurant.setCloseTime(LocalTime.parse(dto.getCloseTime()));
        restaurant.setOpenDays(dto.getOpenDays().stream().map(day -> DayOfWeek.valueOf(day.toUpperCase(Locale.ENGLISH))).collect(Collectors.toList()));

        restaurantValidationService.validateRestaurantDetails(restaurant);

        return restaurantRepository.save(restaurant);
    }

    public Restaurant patchRestaurant(String restaurantId, PatchRestaurantRequestDto dto){
        Restaurant restaurant = getRestaurantById(restaurantId);

        restaurantValidationService.validateRestaurantOwnership(restaurant, authService.getCurrentAuthenticatedUser());

        if (dto.getName() != null) restaurant.setName(dto.getName());
        if (dto.getAddress() != null) restaurant.setAddress(dto.getAddress());
        if (dto.getPhone() != null) restaurant.setPhone(dto.getPhone());
        if (dto.getEmail() != null) restaurant.setEmail(dto.getEmail());
        if (dto.getOpenTime() != null) restaurant.setOpenTime(LocalTime.parse(dto.getOpenTime()));
        if (dto.getCloseTime() != null) restaurant.setCloseTime(LocalTime.parse(dto.getCloseTime()));
        if (dto.getOpenDays() != null) restaurant.setOpenDays(dto.getOpenDays().stream().map(day -> DayOfWeek.valueOf(day.toUpperCase(Locale.ENGLISH))).collect(Collectors.toList()));

        restaurantValidationService.validateRestaurantDetails(restaurant);

        return restaurantRepository.save(restaurant);
    }

    public void deleteRestaurantById(String restaurantId) {
        Restaurant restaurant = getRestaurantById(restaurantId);

        restaurantValidationService.validateRestaurantOwnership(restaurant, authService.getCurrentAuthenticatedUser());

        restaurantRepository.delete(restaurant);
    }
}