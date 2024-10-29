package com.aydnorcn.food_order.service;

import com.aydnorcn.food_order.dto.PageResponseDto;
import com.aydnorcn.food_order.dto.order.CreateOrderRequestDto;
import com.aydnorcn.food_order.entity.*;
import com.aydnorcn.food_order.exception.ResourceNotFoundException;
import com.aydnorcn.food_order.filter.OrderFilter;
import com.aydnorcn.food_order.repository.OrderRepository;
import com.aydnorcn.food_order.service.validation.OrderValidationService;
import com.aydnorcn.food_order.utils.OrderStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserContextService userContextService;
    private final UserService userService;
    private final CartService cartService;
    private final CouponService couponService;
    private final AddressService addressService;
    private final OrderValidationService orderValidationService;

    public Order getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        orderValidationService.validateAuthority(order.getUser());

        return order;
    }

    public PageResponseDto<Order> getOrders(Double minPrice, Double maxPrice, String status, int pageNo, int pageSize) {
        User user = userContextService.getCurrentAuthenticatedUser();

        Specification<Order> specification = OrderFilter.filter(user, minPrice, maxPrice, status);
        Page<Order> page = orderRepository.findAll(specification, PageRequest.of(pageNo, pageSize));

        return new PageResponseDto<>(page);
    }

    public PageResponseDto<Order> getUserOrders(String userId, Double minPrice, Double maxPrice, String status, int pageNo, int pageSize) {
        User user = userService.getUserById(userId);

        orderValidationService.validateAuthority(user);

        Specification<Order> specification = OrderFilter.filter(user, minPrice, maxPrice, status);
        Page<Order> page = orderRepository.findAll(specification, PageRequest.of(pageNo, pageSize));

        return new PageResponseDto<>(page);
    }

    @Transactional
    public Order createOrder(CreateOrderRequestDto dto) {
        User user = userContextService.getCurrentAuthenticatedUser();
        Coupon coupon = (dto.getCouponCode() != null) ? couponService.getCouponByCode(dto.getCouponCode()) : null;

        List<CartItem> items = cartService.findAllByCart(user.getCart());

        if (items.isEmpty()) {
            throw new ResourceNotFoundException("Cart is empty");
        }

        Double total = items.stream().mapToDouble(item -> item.getFood().getPrice() * item.getQuantity()).sum();
        Double discount = (coupon != null) ? total * (coupon.getDiscountPercentage() / 100) : 0;

        Address address = addressService.getAddressById(dto.getAddressId());

        if (!address.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Address not found");
        }

        Order order = new Order(user, dto.getNote(), total - discount, discount, coupon, OrderStatus.PENDING, address);
        List<OrderItem> orderItems = items.stream().map(x -> new OrderItem(order, x.getFood(), x.getQuantity())).toList();
        order.setItems(orderItems);

        cartService.clearCartItems(user.getCart());

        if (coupon != null) {
            couponService.useCoupon(coupon);
        }

        Order savedOrder = orderRepository.save(order);

        log.info("Order successfully created | User id: {}, Order ID: {}, Order Total Price: {}", user.getId(), savedOrder.getId(), savedOrder.getTotal());

        return savedOrder;
    }

    public Order updateOrderStatus(Long orderId, String status) {
        log.info("Updating order status orderId: {}", orderId);
        Order order = getOrderById(orderId);

        orderValidationService.validateAuthority();

        order.setStatus(OrderStatus.fromString(status));
        log.info("Order status successfully updated | orderId: {}, status: {}", order, status);

        return orderRepository.save(order);
    }

    public void deleteOrder(Long orderId) {
        Order order = getOrderById(orderId);

        orderValidationService.validateAuthority(order.getUser());

        orderRepository.delete(order);
        log.info("Order successfully deleted: {}", order.getId());
    }
}