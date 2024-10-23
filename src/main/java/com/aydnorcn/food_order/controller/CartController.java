package com.aydnorcn.food_order.controller;

import com.aydnorcn.food_order.dto.PageResponseDto;
import com.aydnorcn.food_order.dto.cart.CartRequestDto;
import com.aydnorcn.food_order.entity.CartItem;
import com.aydnorcn.food_order.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/carts")
    public ResponseEntity<PageResponseDto<CartItem>> getCart(@RequestParam(name = "page-no", defaultValue = "0", required = false) int pageNo,
                                                             @RequestParam(name = "page-size", defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok(cartService.getCart(pageNo, pageSize));
    }

    @GetMapping("/users/{userId}/carts")
    public ResponseEntity<PageResponseDto<CartItem>> getUserCart(@PathVariable String userId,
                                                                 @RequestParam(name = "page-no", defaultValue = "0", required = false) int pageNo,
                                                                 @RequestParam(name = "page-size", defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok(cartService.getUserCart(userId, pageNo, pageSize));
    }

    @PostMapping("/users/{userId}/carts")
    public ResponseEntity<CartItem> addFoodToUserCart(@PathVariable String userId, @Valid @RequestBody CartRequestDto dto) {
        return ResponseEntity.ok(cartService.addFoodToUserCart(userId, dto));
    }

    @PostMapping("/carts")
    public ResponseEntity<CartItem> addFoodToCart(@Valid @RequestBody CartRequestDto dto) {
        return ResponseEntity.ok(cartService.addFoodToCart(dto));
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