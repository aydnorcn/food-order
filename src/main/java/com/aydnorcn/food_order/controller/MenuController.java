package com.aydnorcn.food_order.controller;

import com.aydnorcn.food_order.dto.PageResponseDto;
import com.aydnorcn.food_order.entity.Food;
import com.aydnorcn.food_order.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/{restaurantId}/menus")
    public ResponseEntity<PageResponseDto<Food>> getRestaurantMenu(@PathVariable String restaurantId,
                                                                   @RequestParam(name = "category-id", required = false) Long categoryId,
                                                                   @RequestParam(name = "page-no", defaultValue = "0", required = false) int pageNo,
                                                                   @RequestParam(name = "page-size", defaultValue = "10", required = false) int pageSize,
                                                                   @RequestParam(name = "min-price", required = false) Double minPrice,
                                                                   @RequestParam(name = "max-price", required = false) Double maxPrice,
                                                                   @RequestParam(name = "sort-by", defaultValue = "id", required = false) String sortBy,
                                                                   @RequestParam(name = "sort-dir", defaultValue = "asc", required = false) String sortDirection) {
        return ResponseEntity.ok(menuService.getRestaurantMenu(restaurantId, categoryId, pageNo, pageSize, minPrice, maxPrice, sortBy, sortDirection));
    }

    @DeleteMapping("/{restaurantId}/menus")
    public ResponseEntity<?> deleteRestaurantMenu(@PathVariable String restaurantId) {
        menuService.deleteRestaurantMenu(restaurantId);

        return ResponseEntity.noContent().build();
    }
}