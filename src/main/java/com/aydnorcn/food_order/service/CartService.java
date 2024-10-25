package com.aydnorcn.food_order.service;

import com.aydnorcn.food_order.dto.PageResponseDto;
import com.aydnorcn.food_order.dto.cart.CartRequestDto;
import com.aydnorcn.food_order.entity.Cart;
import com.aydnorcn.food_order.entity.CartItem;
import com.aydnorcn.food_order.entity.Food;
import com.aydnorcn.food_order.entity.User;
import com.aydnorcn.food_order.exception.ResourceNotFoundException;
import com.aydnorcn.food_order.repository.CartItemRepository;
import com.aydnorcn.food_order.service.validation.CartValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final UserContextService userContextService;
    private final FoodService foodService;
    private final CartValidationService cartValidationService;


    public PageResponseDto<CartItem> getCart(int pageNo, int pageSize) {
        User currentUser = userContextService.getCurrentAuthenticatedUser();
        Page<CartItem> page = cartItemRepository.findByCart(currentUser.getCart(), PageRequest.of(pageNo, pageSize));
        return new PageResponseDto<>(page);
    }

    public PageResponseDto<CartItem> getUserCart(String userId, int pageNo, int pageSize) {
        User user = userService.getUserById(userId);

        cartValidationService.validateAuthority(user);

        Page<CartItem> page = cartItemRepository.findByCart(user.getCart(), PageRequest.of(pageNo, pageSize));
        return new PageResponseDto<>(page);
    }

    public CartItem addFoodToUserCart(String userId, CartRequestDto dto) {
        User user = userService.getUserById(userId);
        return addFoodToCart(user, dto);
    }

    public CartItem addFoodToCart(CartRequestDto dto) {
        User currentUser = userContextService.getCurrentAuthenticatedUser();
        return addFoodToCart(currentUser, dto);
    }

    public void removeFoodFromCart(CartRequestDto dto) {
        User currentUser = userContextService.getCurrentAuthenticatedUser();
        removeFoodFromCart(currentUser, dto);
    }

    public void removeFoodFromUserCart(String userId, CartRequestDto dto) {
        User user = userService.getUserById(userId);
        removeFoodFromCart(user, dto);
    }

    private void removeFoodFromCart(User user, CartRequestDto dto) {
        Food food = foodService.getFoodById(dto.getFoodId());
        CartItem cartItem = cartItemRepository.findByCartAndFood(user.getCart(), food)
                .orElseThrow(() -> new ResourceNotFoundException("Food not found in cart!"));

        cartValidationService.validateAuthority(user);

        if (dto.getQuantity() >= cartItem.getQuantity()) {
            cartItemRepository.delete(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() - dto.getQuantity());
            cartItemRepository.save(cartItem);
        }
    }

    private CartItem addFoodToCart(User user, CartRequestDto dto) {
        Food food = foodService.getFoodById(dto.getFoodId());

        CartItem cartItem = cartItemRepository.findByCartAndFood(user.getCart(), food)
                .orElse(new CartItem(user.getCart(), food, 0));

        cartValidationService.validateAuthority(user);

        cartItem.setQuantity(cartItem.getQuantity() + dto.getQuantity());

        return cartItemRepository.save(cartItem);
    }

    protected void clearCartItems(Cart cart){
        cartItemRepository.deleteAll(cartItemRepository.findAllByCart(cart));
    }

    protected List<CartItem> findAllByCart(Cart cart){
        return cartItemRepository.findAllByCart(cart);
    }
}