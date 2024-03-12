package com.example.dto;

import lombok.Value;

@Value
public class LoginInfo {
    Integer userId;
    Integer currentOrderId;
    String address;
    String role;
    String token;
}
