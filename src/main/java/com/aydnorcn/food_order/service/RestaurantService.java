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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantValidationService restaurantValidationService;
    private final UserContextService userContextService;

    public Restaurant getRestaurantById(String restaurantId) {
        return restaurantRepository.findById(restaurantId).orElseThrow(() -> new ResourceNotFoundException("Restaurant not found!"));
    }

    public PageResponseDto<Restaurant> getRestaurants(int pageNo, int pageSize) {
        return new PageResponseDto<>(restaurantRepository.findAll(PageRequest.of(pageNo, pageSize)));
    }

    public Restaurant createRestaurant(CreateRestaurantRequestDto dto) {
        User currentAuthenticatedUser = userContextService.getCurrentAuthenticatedUser();

        Restaurant restaurant = new Restaurant(dto.getName(), dto.getAddress(), dto.getPhone(), dto.getEmail(),
                currentAuthenticatedUser, LocalTime.parse(dto.getOpenTime()), LocalTime.parse(dto.getCloseTime()),
                dto.getOpenDays().stream().map(day -> DayOfWeek.valueOf(day.toUpperCase(Locale.ENGLISH))).collect(Collectors.toList()));

        restaurantValidationService.validateRestaurantDetails(restaurant);

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        log.info("Restaurant created | restaurantId: {}, ownerId: {}", restaurant.getId(), currentAuthenticatedUser.getId());

        return savedRestaurant;
    }

    public Restaurant updateRestaurantById(String restaurantId, CreateRestaurantRequestDto dto) {
        Restaurant restaurant = getRestaurantById(restaurantId);

        restaurantValidationService.validateAuthority(restaurant, userContextService.getCurrentAuthenticatedUser(), String.format("update restaurant with ID %s", restaurantId));

        restaurant.setName(dto.getName());
        restaurant.setAddress(dto.getAddress());
        restaurant.setPhone(dto.getPhone());
        restaurant.setEmail(dto.getEmail());
        restaurant.setOpenTime(LocalTime.parse(dto.getOpenTime()));
        restaurant.setCloseTime(LocalTime.parse(dto.getCloseTime()));
        restaurant.setOpenDays(dto.getOpenDays().stream().map(day -> DayOfWeek.valueOf(day.toUpperCase(Locale.ENGLISH))).collect(Collectors.toList()));

        restaurantValidationService.validateRestaurantDetails(restaurant);

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        log.info("Restaurant updated | restaurantId: {}, ownerId: {}", restaurant.getId(), userContextService.getCurrentAuthenticatedUser().getId());

        return savedRestaurant;
    }

    public Restaurant patchRestaurant(String restaurantId, PatchRestaurantRequestDto dto) {
        Restaurant restaurant = getRestaurantById(restaurantId);

        restaurantValidationService.validateAuthority(restaurant, userContextService.getCurrentAuthenticatedUser(), String.format("patch restaurant with ID %s", restaurantId));

        if (dto.getName() != null) restaurant.setName(dto.getName());
        if (dto.getAddress() != null) restaurant.setAddress(dto.getAddress());
        if (dto.getPhone() != null) restaurant.setPhone(dto.getPhone());
        if (dto.getEmail() != null) restaurant.setEmail(dto.getEmail());
        if (dto.getOpenTime() != null) restaurant.setOpenTime(LocalTime.parse(dto.getOpenTime()));
        if (dto.getCloseTime() != null) restaurant.setCloseTime(LocalTime.parse(dto.getCloseTime()));
        if (dto.getOpenDays() != null)
            restaurant.setOpenDays(dto.getOpenDays().stream().map(day -> DayOfWeek.valueOf(day.toUpperCase(Locale.ENGLISH))).collect(Collectors.toList()));

        restaurantValidationService.validateRestaurantDetails(restaurant);

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        log.info("Restaurant patched | restaurantId: {}, ownerId: {}", restaurant.getId(), userContextService.getCurrentAuthenticatedUser().getId());

        return savedRestaurant;
    }

    public void deleteRestaurantById(String restaurantId) {
        Restaurant restaurant = getRestaurantById(restaurantId);

        restaurantValidationService.validateAuthority(restaurant, userContextService.getCurrentAuthenticatedUser(), String.format("delete restaurant with ID %s", restaurantId));

        restaurantRepository.delete(restaurant);

        log.info("Restaurant deleted | restaurantId: {}, ownerId: {}", restaurant.getId(), userContextService.getCurrentAuthenticatedUser().getId());
    }
}