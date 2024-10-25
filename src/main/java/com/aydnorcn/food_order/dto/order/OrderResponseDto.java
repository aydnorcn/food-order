package com.aydnorcn.food_order.dto.order;

import com.aydnorcn.food_order.entity.Order;
import lombok.Data;

import java.util.List;

@Data
public class OrderResponseDto {

    private Long id;
    private String userId;
    private List<OrderItemResponseDto> items;
    private String fullName;
    private String address;
    private String city;
    private String phone;
    private String zipCode;
    private String note;
    private Double total;
    private Double discount;
    private Long couponId;
    private String status;

    public OrderResponseDto(Order order) {
        this.id = order.getId();
        this.userId = order.getUser().getId();
        this.items = order.getItems().stream().map(OrderItemResponseDto::new).toList();
        this.fullName = order.getFullName();
        this.address = order.getAddress();
        this.city = order.getCity();
        this.phone = order.getPhone();
        this.zipCode = order.getZipCode();
        this.note = order.getNote();
        this.total = order.getTotal();
        this.discount = order.getDiscount();
        this.couponId = (order.getCoupon() != null) ? order.getCoupon().getId() : null;
        this.status = order.getStatus().name();
    }
}