package com.aydnorcn.food_order.controller;

import com.aydnorcn.food_order.dto.PageResponseDto;
import com.aydnorcn.food_order.entity.Favorite;
import com.aydnorcn.food_order.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping("/favorites")
    public ResponseEntity<PageResponseDto<Favorite>> getFavoriteFoods(@RequestParam(name = "page-no", defaultValue = "0", required = false) int pageNo,
                                                            @RequestParam(name = "page-size", defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok(favoriteService.getFavoriteFoods(pageNo, pageSize));
    }

    @GetMapping("/users/{userId}/favorites")
    public ResponseEntity<PageResponseDto<Favorite>> getUserFavoriteFoods(@PathVariable String userId,
                                               @RequestParam(name = "page-no", defaultValue = "0", required = false) int pageNo,
                                               @RequestParam(name = "page-size", defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok(favoriteService.getUserFavoriteFoods(userId, pageNo, pageSize));
    }

    @PostMapping("/foods/{foodId}/favorites")
    public ResponseEntity<Favorite> addFoodToFavorite(@PathVariable Long foodId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(favoriteService.addFoodToFavorite(foodId));
    }

    @DeleteMapping("/foods/{foodId}/favorites")
    public ResponseEntity<?> removeFoodFromFavorite(@PathVariable Long foodId) {
        favoriteService.removeFoodFromFavorite(foodId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/users/{userId}/favorites")
    public ResponseEntity<?> removeUserFavorites(@PathVariable String userId) {
        favoriteService.removeUserFavorites(userId);
        return ResponseEntity.noContent().build();
    }
}
