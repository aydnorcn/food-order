package com.aydnorcn.food_order.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    private String email;
    private String token;

    public LoginResponse(String email, String token) {
        this.email = email;
        this.token = token;
    }
}
