package com.aydnorcn.food_order.controller;


import com.aydnorcn.food_order.dto.PageResponseDto;
import com.aydnorcn.food_order.dto.food.CreateFoodRequestDto;
import com.aydnorcn.food_order.dto.food.PatchFoodRequestDto;
import com.aydnorcn.food_order.entity.Food;
import com.aydnorcn.food_order.service.FoodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @GetMapping("/{foodId}")
    public ResponseEntity<Food> getFoodById(@PathVariable Long foodId) {
        return ResponseEntity.ok(foodService.getFoodById(foodId));
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<Food>> getFoods(@RequestParam(name = "category-id", required = false) Long categoryId,
                                                          @RequestParam(name = "restaurant-id", required = false) String restaurantId,
                                                          @RequestParam(name = "min-price", required = false) Double minPrice,
                                                          @RequestParam(name = "max-price", required = false) Double maxPrice,
                                                          @RequestParam(name = "page-no", defaultValue = "0", required = false) int pageNo,
                                                          @RequestParam(name = "page-size", defaultValue = "10", required = false) int pageSize,
                                                          @RequestParam(name = "sort-by", defaultValue = "id", required = false) String sortBy,
                                                          @RequestParam(name = "sort-dir", defaultValue = "asc", required = false) String sortDirection) {
        return ResponseEntity.ok(foodService.getFoods(categoryId, restaurantId, minPrice, maxPrice, pageNo, pageSize, sortBy, sortDirection));
    }

    @PostMapping
    public ResponseEntity<Food> createFood(@Valid @RequestPart("data") CreateFoodRequestDto dto, @RequestPart MultipartFile image) {
        dto.setImage(image);
        return ResponseEntity.status(HttpStatus.CREATED).body(foodService.createFood(dto));
    }

    @PutMapping("/{foodId}")
    public ResponseEntity<Food> updateFood(@PathVariable Long foodId, @Valid @RequestPart("data") CreateFoodRequestDto dto, @RequestPart MultipartFile image) {
        dto.setImage(image);
        return ResponseEntity.ok(foodService.updateFood(foodId, dto));
    }

    @PatchMapping("/{foodId}")
    public ResponseEntity<Food> patchFood(@PathVariable Long foodId, @Valid @RequestPart("data") PatchFoodRequestDto dto, @RequestPart MultipartFile image) {
        dto.setImage(image);
        return ResponseEntity.ok(foodService.patchFood(foodId, dto));
    }

    @DeleteMapping("/{foodId}")
    public ResponseEntity<?> deleteFood(@PathVariable Long foodId) {
        foodService.deleteFood(foodId);
        return ResponseEntity.noContent().build();
    }
}