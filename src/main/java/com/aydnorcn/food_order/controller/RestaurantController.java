package com.aydnorcn.food_order.controller;

import com.aydnorcn.food_order.dto.PageResponseDto;
import com.aydnorcn.food_order.dto.restaurant.CreateRestaurantRequestDto;
import com.aydnorcn.food_order.dto.restaurant.PatchRestaurantRequestDto;
import com.aydnorcn.food_order.dto.restaurant.RestaurantResponseDto;
import com.aydnorcn.food_order.entity.Restaurant;
import com.aydnorcn.food_order.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponseDto> getRestaurantById(@PathVariable String restaurantId) {
        return ResponseEntity.ok(new RestaurantResponseDto(restaurantService.getRestaurantById(restaurantId)));
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<RestaurantResponseDto>> getRestaurants(@RequestParam(name = "page-no", required = false, defaultValue = "0") int pageNo,
                                                      @RequestParam(name = "page-size", required = false, defaultValue = "10") int pageSize) {
        PageResponseDto<Restaurant> restaurants = restaurantService.getRestaurants(pageNo, pageSize);
        List<RestaurantResponseDto> restaurantResponses = restaurants.getContent().stream().map(RestaurantResponseDto::new).toList();
        return ResponseEntity.ok(new PageResponseDto<>(restaurantResponses, restaurants.getPageNo(), restaurants.getPageSize(), restaurants.getTotalElements(),restaurants.getTotalPages()));
    }

    @PostMapping
    public ResponseEntity<RestaurantResponseDto> createRestaurant(@Valid @RequestBody CreateRestaurantRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new RestaurantResponseDto(restaurantService.createRestaurant(dto)));
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponseDto> updateRestaurantById(@PathVariable String restaurantId, @Valid @RequestBody CreateRestaurantRequestDto dto) {
        return ResponseEntity.ok(new RestaurantResponseDto(restaurantService.updateRestaurantById(restaurantId, dto)));
    }

    @PatchMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponseDto> patchRestaurant(@PathVariable String restaurantId, @Valid @RequestBody PatchRestaurantRequestDto dto) {
        return ResponseEntity.ok(new RestaurantResponseDto(restaurantService.patchRestaurant(restaurantId, dto)));
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<?> deleteRestaurantById(@PathVariable String restaurantId) {
        restaurantService.deleteRestaurantById(restaurantId);
        return ResponseEntity.noContent().build();
    }
}