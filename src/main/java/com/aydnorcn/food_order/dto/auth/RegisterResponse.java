package com.aydnorcn.food_order.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponse {

    private String email;
    private String firstName;
    private String lastName;

    public RegisterResponse(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
