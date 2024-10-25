package com.aydnorcn.food_order.utils;

public enum OrderStatus {
    PENDING,
    CONFIRMED,
    DELIVERED,
    CANCELLED;

    public static OrderStatus fromString(String status) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.name().equalsIgnoreCase(status)) {
                return orderStatus;
            }
        }
        return null;
    }
}
