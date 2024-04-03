package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryListDto extends AbstractDto {

    private Integer deliveryId;

    private LocalDate dateArrived;

    private String paymentMethod;

    private Long orderId;
}
