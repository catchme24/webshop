package com.example.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuccesLoginDto {
    private String userId;
    private String currentOrderId;
    private String role;
    private String token;
    private String address;
}
