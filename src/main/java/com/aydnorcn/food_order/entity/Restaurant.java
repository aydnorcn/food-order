package com.aydnorcn.food_order.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@Table(name = "restaurants")
@NoArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String address;
    private String phone;
    private String email;

    public Restaurant(String name, String address, String phone, String email, User owner, LocalTime openTime, LocalTime closeTime, List<DayOfWeek> openDays) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.owner = owner;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.openDays = openDays;
    }

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    private LocalTime openTime;
    private LocalTime closeTime;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(name = "restaurant_open_days", joinColumns = @JoinColumn(name = "restaurant_id"))
    private List<DayOfWeek> openDays;


    @Override
    public String toString() {
        return "Restaurant(" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", owner=" + owner.getId() +
                ", openTime=" + openTime +
                ", closeTime=" + closeTime +
                ", openDays=" + openDays +
                ')';
    }
}