package com.example.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto extends AbstractDto {

    private Integer orderId;

    private LocalDate dateGet;

    private Long customerId;

    private DeliveryListDto deliveryList;

    private List<ProductQuantityDto> productsWithQuantity;
}