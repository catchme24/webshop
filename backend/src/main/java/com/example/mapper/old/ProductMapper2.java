package com.example.mapper.old;

import com.example.dto.order.ProductDto;
import com.example.entity.Product;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductMapper2 implements MapperSecond<Product, ProductDto> {

    private ModelMapper modelMapper;

    @Override
    public Product toEntity(ProductDto dto) {
        return null;
    }

    @Override
    public ProductDto toDto(Product entity) {
        return modelMapper.map(entity, ProductDto.class);
    }

    @Override
    public ProductDto toDto(Product entity, ProductDto dto) {
        return null;
    }
}
