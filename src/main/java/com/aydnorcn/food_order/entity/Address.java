package com.aydnorcn.food_order.entity;

import com.aydnorcn.food_order.dto.address.CreateAddressRequestDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String address;
    private String city;
    private String phone;
    private String zipCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Address(CreateAddressRequestDto dto, User user){
        this.fullName = dto.getFullName();
        this.address = dto.getAddress();
        this.city = dto.getCity();
        this.phone = dto.getPhone();
        this.zipCode = dto.getZipCode();
        this.user = user;
    }
}
