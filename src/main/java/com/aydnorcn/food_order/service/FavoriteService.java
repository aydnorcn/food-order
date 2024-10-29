package com.aydnorcn.food_order.service;

import com.aydnorcn.food_order.dto.PageResponseDto;
import com.aydnorcn.food_order.entity.Favorite;
import com.aydnorcn.food_order.entity.Food;
import com.aydnorcn.food_order.entity.User;
import com.aydnorcn.food_order.exception.AlreadyExistsException;
import com.aydnorcn.food_order.exception.NoAuthorityException;
import com.aydnorcn.food_order.exception.ResourceNotFoundException;
import com.aydnorcn.food_order.repository.FavoriteRepository;
import com.aydnorcn.food_order.service.validation.FavoriteValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final FoodService foodService;
    private final UserContextService userContextService;
    private final UserService userService;
    private final FavoriteValidationService favoriteValidationService;

    public PageResponseDto<Favorite> getFavoriteFoods(int pageNo, int pageSize) {
        User currentUser = userContextService.getCurrentAuthenticatedUser();

        Page<Favorite> page = favoriteRepository.findAllByUser(currentUser, PageRequest.of(pageNo, pageSize));

        return new PageResponseDto<>(page);
    }

    public PageResponseDto<Favorite> getUserFavoriteFoods(String userId, int pageNo, int pageSize) {
        User user = userService.getUserById(userId);

        favoriteValidationService.validateAuthority(user, String.format("view favorites of user with ID %s", userId));

        Page<Favorite> page = favoriteRepository.findAllByUser(user, PageRequest.of(pageNo, pageSize));

        return new PageResponseDto<>(page);
    }

    public Favorite addFoodToFavorite(Long foodId) {
        Food food = foodService.getFoodById(foodId);
        User currentUser = userContextService.getCurrentAuthenticatedUser();

        if (favoriteRepository.existsByUserAndFood(currentUser, food)) {
            throw new AlreadyExistsException("Favorite already exists!");
        }

        Favorite favorite = new Favorite(currentUser, food);

        return favoriteRepository.save(favorite);
    }

    public void removeFoodFromFavorite(Long foodId) {
        Food food = foodService.getFoodById(foodId);
        User currentUser = userContextService.getCurrentAuthenticatedUser();

        Favorite favorite = favoriteRepository.findByUserAndFood(currentUser, food).orElseThrow(() -> new ResourceNotFoundException("Favorite not found!"));

        favoriteRepository.delete(favorite);
    }

    public void removeUserFavorites(String userId) {
        User user = userService.getUserById(userId);

        favoriteValidationService.validateAuthority(user, String.format("remove favorites of user with ID %s", userId));

        favoriteRepository.deleteAllByUser(user);
    }
}