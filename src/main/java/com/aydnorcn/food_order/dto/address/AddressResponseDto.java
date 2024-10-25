package com.aydnorcn.food_order.dto.address;

import com.aydnorcn.food_order.entity.Address;
import lombok.Data;

@Data
public class AddressResponseDto {

    private String fullName;
    private String address;
    private String city;
    private String phone;
    private String zipCode;

    private String userId;

    public AddressResponseDto(Address address){
        this.fullName = address.getFullName();
        this.address = address.getAddress();
        this.city = address.getCity();
        this.phone = address.getPhone();
        this.zipCode = address.getZipCode();
        this.userId = address.getUser().getId();
    }
}
