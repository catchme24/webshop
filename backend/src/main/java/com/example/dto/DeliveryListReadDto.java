package com.example.dto;

import lombok.Value;
import java.time.LocalDate;

@Value
public class DeliveryListReadDto {

    Integer deliveryId;

    LocalDate dateArrived;

    String paymentMethod;
}
