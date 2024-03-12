package com.example.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class ProductReadDto {

    Integer productId;

    String productName;

    BigDecimal price;

    String description;
    String imageUrl;
}
