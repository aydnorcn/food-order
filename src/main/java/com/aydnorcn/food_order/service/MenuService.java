package com.aydnorcn.food_order.service;

import com.aydnorcn.food_order.dto.PageResponseDto;
import com.aydnorcn.food_order.entity.Food;
import com.aydnorcn.food_order.entity.Restaurant;
import com.aydnorcn.food_order.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final FoodService foodService;
    private final FoodRepository foodRepository;
    private final RestaurantService restaurantService;
    private final UserContextService userContextService;

    public PageResponseDto<Food> getRestaurantMenu(String restaurantId, Long categoryId, int pageNo, int pageSize, Double minPrice, Double maxPrice, String sortBy, String sortDirection) {
        return foodService.getFoods(categoryId, restaurantId, minPrice, maxPrice, pageNo, pageSize, sortBy, sortDirection);
    }

    public void deleteRestaurantMenu(String restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);

        if (!userContextService.getCurrentAuthenticatedUser().getId().equals(restaurant.getOwner().getId()) &&
                !userContextService.isCurrentAuthenticatedUserAdmin() && !userContextService.isCurrentAuthenticatedUserStaff()) {
            throw new RuntimeException("You are not authorized to delete this restaurant's menu");
        }
        foodRepository.findAllByRestaurantId(restaurantId).forEach(food -> foodService.deleteFood(food.getId()));
    }
}