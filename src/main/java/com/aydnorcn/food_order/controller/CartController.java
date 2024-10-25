package com.aydnorcn.food_order.controller;

import com.aydnorcn.food_order.dto.PageResponseDto;
import com.aydnorcn.food_order.dto.cart.CartItemResponseDto;
import com.aydnorcn.food_order.dto.cart.CartRequestDto;
import com.aydnorcn.food_order.entity.CartItem;
import com.aydnorcn.food_order.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/carts")
    public ResponseEntity<PageResponseDto<CartItemResponseDto>> getCart(@RequestParam(name = "page-no", defaultValue = "0", required = false) int pageNo,
                                                                        @RequestParam(name = "page-size", defaultValue = "10", required = false) int pageSize) {

        PageResponseDto<CartItem> items = cartService.getCart(pageNo, pageSize);
        List<CartItemResponseDto> cartItems = items.getContent().stream().map(CartItemResponseDto::new).toList();
        return ResponseEntity.ok(new PageResponseDto<>(cartItems, items.getPageNo(), items.getPageSize(), items.getTotalElements(), items.getTotalPages()));
    }

    @GetMapping("/users/{userId}/carts")
    public ResponseEntity<PageResponseDto<CartItemResponseDto>> getUserCart(@PathVariable String userId,
                                                                            @RequestParam(name = "page-no", defaultValue = "0", required = false) int pageNo,
                                                                            @RequestParam(name = "page-size", defaultValue = "10", required = false) int pageSize) {
        PageResponseDto<CartItem> items = cartService.getUserCart(userId, pageNo, pageSize);
        List<CartItemResponseDto> cartItems = items.getContent().stream().map(CartItemResponseDto::new).toList();
        return ResponseEntity.ok(new PageResponseDto<>(cartItems, items.getPageNo(), items.getPageSize(), items.getTotalElements(), items.getTotalPages()));
    }

    @PostMapping("/users/{userId}/carts")
    public ResponseEntity<CartItemResponseDto> addFoodToUserCart(@PathVariable String userId, @Valid @RequestBody CartRequestDto dto) {
        return ResponseEntity.ok(new CartItemResponseDto(cartService.addFoodToUserCart(userId, dto)));
    }

    @PostMapping("/carts")
    public ResponseEntity<CartItemResponseDto> addFoodToCart(@Valid @RequestBody CartRequestDto dto) {
        return ResponseEntity.ok(new CartItemResponseDto(cartService.addFoodToCart(dto)));
    }

    @DeleteMapping("/carts")
    public ResponseEntity<?> removeFoodFromCart(@Valid @RequestBody CartRequestDto dto) {
        cartService.removeFoodFromCart(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/users/{userId}/carts")
    public ResponseEntity<?> removeFoodFromUserCart(@PathVariable String userId, @Valid @RequestBody CartRequestDto dto) {
        cartService.removeFoodFromUserCart(userId, dto);
        return ResponseEntity.noContent().build();
    }
}