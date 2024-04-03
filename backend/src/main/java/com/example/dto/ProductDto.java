package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto extends AbstractDto {

    private Integer productId;

    private String productName;

    private BigDecimal price;

    private String description;

    private String imageUrl;
}
