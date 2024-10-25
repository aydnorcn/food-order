package com.aydnorcn.food_order.controller;

import com.aydnorcn.food_order.dto.PageResponseDto;
import com.aydnorcn.food_order.dto.order.CreateOrderRequestDto;
import com.aydnorcn.food_order.entity.Order;
import com.aydnorcn.food_order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @GetMapping("/orders")
    public ResponseEntity<PageResponseDto<Order>> getOrders(@RequestParam(name = "page-no", defaultValue = "0") int pageNo,
                                                            @RequestParam(name = "page-size", defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(orderService.getOrders(pageNo, pageSize));
    }

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<PageResponseDto<Order>> getUserOrders(@PathVariable String userId,
                                                                @RequestParam(name = "page-no", defaultValue = "0") int pageNo,
                                                                @RequestParam(name = "page-size", defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(orderService.getUserOrders(userId, pageNo, pageSize));
    }

    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody CreateOrderRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(dto));
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
