package com.aydnorcn.food_order.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    @Override
    public String toString() {
        return "Role(" +
                "id=" + id +
                ", name='" + name + '\'' +
                ')';
    }
}
