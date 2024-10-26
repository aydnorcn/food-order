package com.aydnorcn.food_order.controller;

import com.aydnorcn.food_order.dto.PageResponseDto;
import com.aydnorcn.food_order.dto.order.CreateOrderRequestDto;
import com.aydnorcn.food_order.dto.order.OrderResponseDto;
import com.aydnorcn.food_order.entity.Order;
import com.aydnorcn.food_order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(new OrderResponseDto(orderService.getOrderById(orderId)));
    }

    @GetMapping("/orders")
    public ResponseEntity<PageResponseDto<OrderResponseDto>> getOrders(@RequestParam(name = "min-price", required = false) Double minPrice,
                                                                       @RequestParam(name = "max-price", required = false) Double maxPrice,
                                                                       @RequestParam(name = "status", required = false) String status,
                                                                       @RequestParam(name = "page-no", defaultValue = "0") int pageNo,
                                                                       @RequestParam(name = "page-size", defaultValue = "10") int pageSize) {
        PageResponseDto<Order> orders = orderService.getOrders(minPrice, maxPrice, status, pageNo, pageSize);
        List<OrderResponseDto> orderResponses = orders.getContent().stream().map(OrderResponseDto::new).toList();
        return ResponseEntity.ok(new PageResponseDto<>(orderResponses, orders.getPageNo(), orders.getPageSize(), orders.getTotalElements(), orders.getTotalPages()));
    }

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<PageResponseDto<OrderResponseDto>> getUserOrders(@PathVariable String userId,
                                                                           @RequestParam(name = "min-price", required = false) Double minPrice, @RequestParam(name = "max-price", required = false) Double maxPrice,
                                                                           @RequestParam(name = "status", required = false) String status,
                                                                           @RequestParam(name = "page-no", defaultValue = "0") int pageNo,
                                                                           @RequestParam(name = "page-size", defaultValue = "10") int pageSize) {
        PageResponseDto<Order> orders = orderService.getUserOrders(userId, minPrice, maxPrice, status, pageNo, pageSize);
        List<OrderResponseDto> orderResponses = orders.getContent().stream().map(OrderResponseDto::new).toList();
        return ResponseEntity.ok(new PageResponseDto<>(orderResponses, orders.getPageNo(), orders.getPageSize(), orders.getTotalElements(), orders.getTotalPages()));
    }

    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        return ResponseEntity.ok(new OrderResponseDto(orderService.updateOrderStatus(orderId, status)));
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody CreateOrderRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new OrderResponseDto(orderService.createOrder(dto)));
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
