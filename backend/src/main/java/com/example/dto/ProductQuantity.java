package com.example.dto;

import lombok.Value;

@Value
public class ProductQuantity {

    ProductReadDto product;
    Integer quantity;
}
