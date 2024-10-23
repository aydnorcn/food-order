package com.aydnorcn.food_order.service;

import com.aydnorcn.food_order.dto.PageResponseDto;
import com.aydnorcn.food_order.dto.food.CreateFoodRequestDto;
import com.aydnorcn.food_order.dto.food.PatchFoodRequestDto;
import com.aydnorcn.food_order.entity.Category;
import com.aydnorcn.food_order.entity.Food;
import com.aydnorcn.food_order.entity.Restaurant;
import com.aydnorcn.food_order.exception.ResourceNotFoundException;
import com.aydnorcn.food_order.filter.FoodFilter;
import com.aydnorcn.food_order.repository.FoodRepository;
import com.aydnorcn.food_order.service.validation.FoodValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;
    private final RestaurantService restaurantService;
    private final ImageService imageService;
    private final CategoryService categoryService;
    private final FoodValidationService foodValidationService;

    public Food getFoodById(Long foodId) {
        return foodRepository.findById(foodId).orElseThrow(() -> new ResourceNotFoundException("Food not found!"));
    }

    public PageResponseDto<Food> getFoods(Long categoryId, String restaurantId, Double minPrice, Double maxPrice, int pageNo, int pageSize, String sortBy, String sortDirection) {
        Category category = (categoryId != null) ? categoryService.getCategoryById(categoryId) : null;
        Restaurant restaurant = (restaurantId != null) ? restaurantService.getRestaurantById(restaurantId) : null;

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Specification<Food> specification = FoodFilter.filter(category, restaurant, minPrice, maxPrice);
        Page<Food> foods = foodRepository.findAll(specification, PageRequest.of(pageNo, pageSize, sort));

        return new PageResponseDto<>(foods);
    }

    public Food createFood(CreateFoodRequestDto dto) {
        Restaurant restaurant = restaurantService.getRestaurantById(dto.getRestaurantId());

        foodValidationService.validateAuthority(restaurant);

        String imageUrl = imageService.saveImage(dto.getImage());
        Category category = categoryService.getCategoryById(dto.getCategoryId());

        Food food = new Food(dto.getName(), dto.getDescription(), dto.getPrice(), imageUrl, restaurant, category);

        return foodRepository.save(food);
    }

    public Food updateFood(Long foodId, CreateFoodRequestDto dto) {
        Food food = getFoodById(foodId);

        foodValidationService.validateAuthority(food.getRestaurant());

        String imageUrl = imageService.saveImage(dto.getImage());
        Category category = categoryService.getCategoryById(dto.getCategoryId());

        food.setName(dto.getName());
        food.setDescription(dto.getDescription());
        food.setPrice(dto.getPrice());
        food.setImageUrl(imageUrl);
        food.setCategory(category);

        return foodRepository.save(food);
    }

    public Food patchFood(Long foodId, PatchFoodRequestDto dto) {
        Food food = getFoodById(foodId);

        foodValidationService.validateAuthority(food.getRestaurant());

        if (dto.getName() != null) food.setName(dto.getName());
        if (dto.getDescription() != null) food.setDescription(dto.getDescription());
        if (dto.getPrice() != null) food.setPrice(dto.getPrice());
        if (dto.getImage() != null) food.setImageUrl(imageService.saveImage(dto.getImage()));
        if (dto.getCategoryId() != null) food.setCategory(categoryService.getCategoryById(dto.getCategoryId()));

        return foodRepository.save(food);
    }

    public void deleteFood(Long foodId) {
        Food food = getFoodById(foodId);

        foodValidationService.validateAuthority(food.getRestaurant());

        foodRepository.deleteById(foodId);
    }
}