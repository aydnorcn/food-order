package com.aydnorcn.food_order.controller;

import com.aydnorcn.food_order.dto.PageResponseDto;
import com.aydnorcn.food_order.dto.restaurant.CreateRestaurantRequestDto;
import com.aydnorcn.food_order.dto.restaurant.PatchRestaurantRequestDto;
import com.aydnorcn.food_order.entity.Restaurant;
import com.aydnorcn.food_order.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable String restaurantId) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(restaurantId));
    }

    @GetMapping
    public PageResponseDto<Restaurant> getRestaurants(@RequestParam(name = "page-no", required = false, defaultValue = "0") int pageNo,
                                                      @RequestParam(name = "page-size", required = false, defaultValue = "10") int pageSize) {
        return restaurantService.getRestaurants(pageNo, pageSize);
    }

    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody CreateRestaurantRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.createRestaurant(dto));
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> updateRestaurantById(@PathVariable String restaurantId, @Valid @RequestBody CreateRestaurantRequestDto dto) {
        return ResponseEntity.ok(restaurantService.updateRestaurantById(restaurantId, dto));
    }

    @PatchMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> patchRestaurant(@PathVariable String restaurantId, @Valid @RequestBody PatchRestaurantRequestDto dto) {
        return ResponseEntity.ok(restaurantService.patchRestaurant(restaurantId, dto));
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<?> deleteRestaurantById(@PathVariable String restaurantId) {
        restaurantService.deleteRestaurantById(restaurantId);
        return ResponseEntity.noContent().build();
    }
}