package com.aydnorcn.food_order.dto.food;

import com.aydnorcn.food_order.dto.restaurant.RestaurantResponseDto;
import com.aydnorcn.food_order.entity.Category;
import com.aydnorcn.food_order.entity.Food;
import lombok.Data;

@Data
public class FoodResponseDto {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private Category category;
    private RestaurantResponseDto restaurant;

    public FoodResponseDto(Food food) {
        this.id = food.getId();
        this.name = food.getName();
        this.description = food.getDescription();
        this.price = food.getPrice();
        this.imageUrl = food.getImageUrl();
        this.category = food.getCategory();
        this.restaurant = new RestaurantResponseDto(food.getRestaurant());
    }
}