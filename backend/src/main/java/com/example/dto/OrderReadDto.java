package com.example.dto;

import lombok.Value;
import java.time.LocalDate;
import java.util.List;

@Value
public class OrderReadDto {

    Integer orderId;

    LocalDate dateGet;

    DeliveryListReadDto deliveryListReadDto;

    List<ProductQuantity> products;

}
