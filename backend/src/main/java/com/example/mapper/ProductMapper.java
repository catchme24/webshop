package com.example.mapper;

import com.example.dto.order.ProductDto;
import com.example.entity.Product;
import com.example.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper extends AbstractMapper<Product, ProductDto> {

    private final ModelMapper mapper;
    private final ProductRepository productRepository;

    @Autowired
    public ProductMapper(ModelMapper mapper, ProductRepository productRepository) {
        super(Product.class, ProductDto.class);
        this.mapper = mapper;
        this.productRepository = productRepository;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Product.class, ProductDto.class)
                .addMappings(m -> {

                }).setPostConverter(toDtoConverter());

        mapper.createTypeMap(ProductDto.class, Product.class)
                .addMappings(m -> {

                }).setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificFields(Product source, ProductDto destination) {

    }

    @Override
    public void mapSpecificFields(ProductDto source, Product destination) {

    }

}
