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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public PageResponseDto<Order> getOrders(Double minPrice, Double maxPrice, String status, int pageNo, int pageSize) {
        User user = userContextService.getCurrentAuthenticatedUser();

        Specification<Order> specification = OrderFilter.filter(minPrice, maxPrice, status);
        Page<Order> page = orderRepository.findAllByUser(user, specification, PageRequest.of(pageNo, pageSize));

        return new PageResponseDto<>(page);
    }

    public PageResponseDto<Order> getUserOrders(String userId,Double minPrice, Double maxPrice, String status, int pageNo, int pageSize) {
        User user = userService.getUserById(userId);

        orderValidationService.validateAuthority(user);

        Specification<Order> specification = OrderFilter.filter(minPrice, maxPrice, status);
        Page<Order> page = orderRepository.findAllByUser(user, specification, PageRequest.of(pageNo, pageSize));

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
        Double discount = (coupon != null) ? total * coupon.getDiscountPercentage() : 0;

        Address address = addressService.getAddressById(dto.getAddressId());

        if(!address.getUser().getId().equals(user.getId())){
            throw new ResourceNotFoundException("Address not found");
        }

        Order order = new Order(user, dto.getNote(), total - discount, discount, coupon, OrderStatus.PENDING, address);
        List<OrderItem> orderItems = items.stream().map(x -> new OrderItem(order,x.getFood(),x.getQuantity())).toList();
        order.setItems(orderItems);

        cartService.clearCartItems(user.getCart());

        if(coupon != null){
            couponService.useCoupon(coupon);
        }

        return orderRepository.save(order);
    }

    public Order updateOrderStatus(Long orderId, String status) {
        Order order = getOrderById(orderId);

        orderValidationService.validateAuthority();

        order.setStatus(OrderStatus.fromString(status));

        return orderRepository.save(order);
    }

    public void deleteOrder(Long orderId) {
        Order order = getOrderById(orderId);

        orderValidationService.validateAuthority(order.getUser());

        orderRepository.delete(order);
    }
}