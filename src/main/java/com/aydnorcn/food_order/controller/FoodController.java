package com.aydnorcn.food_order.controller;


import com.aydnorcn.food_order.dto.PageResponseDto;
import com.aydnorcn.food_order.dto.food.CreateFoodRequestDto;
import com.aydnorcn.food_order.dto.food.FoodResponseDto;
import com.aydnorcn.food_order.dto.food.PatchFoodRequestDto;
import com.aydnorcn.food_order.entity.Food;
import com.aydnorcn.food_order.service.FoodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @GetMapping("/{foodId}")
    public ResponseEntity<FoodResponseDto> getFoodById(@PathVariable Long foodId) {
        return ResponseEntity.ok(new FoodResponseDto(foodService.getFoodById(foodId)));
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<FoodResponseDto>> getFoods(@RequestParam(name = "category-id", required = false) Long categoryId,
                                                                     @RequestParam(name = "restaurant-id", required = false) String restaurantId,
                                                                     @RequestParam(name = "min-price", required = false) Double minPrice,
                                                                     @RequestParam(name = "max-price", required = false) Double maxPrice,
                                                                     @RequestParam(name = "page-no", defaultValue = "0", required = false) int pageNo,
                                                                     @RequestParam(name = "page-size", defaultValue = "10", required = false) int pageSize,
                                                                     @RequestParam(name = "sort-by", defaultValue = "id", required = false) String sortBy,
                                                                     @RequestParam(name = "sort-dir", defaultValue = "asc", required = false) String sortDirection) {
        PageResponseDto<Food> foods = foodService.getFoods(categoryId, restaurantId, minPrice, maxPrice, pageNo, pageSize, sortBy, sortDirection);
        List<FoodResponseDto> foodResponses = foods.getContent().stream().map(FoodResponseDto::new).toList();

        return ResponseEntity.ok(new PageResponseDto<>(foodResponses, foods.getPageNo(), foods.getPageSize(), foods.getTotalElements(), foods.getTotalPages()));
    }

    @PostMapping
    public ResponseEntity<FoodResponseDto> createFood(@Valid @RequestPart("data") CreateFoodRequestDto dto, @RequestPart MultipartFile image) {
        dto.setImage(image);
        return ResponseEntity.status(HttpStatus.CREATED).body(new FoodResponseDto(foodService.createFood(dto)));
    }

    @PutMapping("/{foodId}")
    public ResponseEntity<FoodResponseDto> updateFood(@PathVariable Long foodId, @Valid @RequestPart("data") CreateFoodRequestDto dto, @RequestPart MultipartFile image) {
        dto.setImage(image);
        return ResponseEntity.ok(new FoodResponseDto(foodService.updateFood(foodId, dto)));
    }

    @PatchMapping("/{foodId}")
    public ResponseEntity<FoodResponseDto> patchFood(@PathVariable Long foodId, @Valid @RequestPart("data") PatchFoodRequestDto dto, @RequestPart MultipartFile image) {
        dto.setImage(image);
        return ResponseEntity.ok(new FoodResponseDto(foodService.patchFood(foodId, dto)));
    }

    @DeleteMapping("/{foodId}")
    public ResponseEntity<?> deleteFood(@PathVariable Long foodId) {
        foodService.deleteFood(foodId);
        return ResponseEntity.noContent().build();
    }
}