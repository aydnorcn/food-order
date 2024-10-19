package com.aydnorcn.food_order.dto.restaurant;

import lombok.Data;

import java.util.List;

@Data
public class PatchRestaurantRequestDto {
    private String name;
    private String address;
    private String phone;
    private String email;
    private String openTime;
    private String closeTime;
    private List<String> openDays;
}
