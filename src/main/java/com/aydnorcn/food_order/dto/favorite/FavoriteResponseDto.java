package com.aydnorcn.food_order.dto.favorite;

import com.aydnorcn.food_order.dto.food.FoodResponseDto;
import com.aydnorcn.food_order.entity.Favorite;
import lombok.Data;

@Data
public class FavoriteResponseDto {

    private Long id;
    private String userId;
    private FoodResponseDto food;

    public FavoriteResponseDto(Favorite favorite) {
        this.id = favorite.getId();
        this.userId = favorite.getUser().getId();
        this.food = new FoodResponseDto(favorite.getFood());
    }
}
