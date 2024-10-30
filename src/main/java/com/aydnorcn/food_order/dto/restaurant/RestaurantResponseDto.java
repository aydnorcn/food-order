package com.aydnorcn.food_order.dto.restaurant;

import com.aydnorcn.food_order.entity.Restaurant;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.List;

@Data
public class RestaurantResponseDto {

    private String id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String openTime;
    private String closeTime;
    private List<String> openDays;
    private String ownerId;

    public RestaurantResponseDto(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.address = restaurant.getAddress();
        this.phone = restaurant.getPhone();
        this.email = restaurant.getEmail();
        this.openTime = restaurant.getOpenTime().toString();
        this.closeTime = restaurant.getCloseTime().toString();
        this.openDays = restaurant.getOpenDays().stream().map(DayOfWeek::toString).toList();
        this.ownerId = restaurant.getOwner().getId();
    }
}