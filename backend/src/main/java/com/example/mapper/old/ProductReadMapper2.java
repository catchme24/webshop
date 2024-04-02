package com.example.mapper.old;

import com.example.dto.ProductReadDto;
import com.example.entity.Product;
import com.example.mapper.old.Mapper2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductReadMapper2 implements Mapper2<Product, ProductReadDto> {
    @Override
    public ProductReadDto map(Product object) {
        return new ProductReadDto(
                object.getProductId(),
                object.getProductName(),
                object.getPrice(),
                object.getDescription(),
                object.getImageUrl());
    }
}
