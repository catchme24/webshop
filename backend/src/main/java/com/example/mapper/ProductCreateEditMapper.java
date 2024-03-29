package com.example.mapper;

import com.example.dto.ProductCreateEditDto;
import com.example.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductCreateEditMapper implements Mapper<ProductCreateEditDto, Product>{

    @Override
    public Product map(ProductCreateEditDto fromObject, Product toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public Product map(ProductCreateEditDto object) {
        Product product = new Product();
        copy(object, product);
        return product;
    }

    private void copy(ProductCreateEditDto object, Product toObject){
        toObject.setProductId(object.getProductId());
        toObject.setProductName(object.getProductName());
        toObject.setPrice(object.getPrice());
        toObject.setDescription(object.getDescription());
        toObject.setImageUrl(object.getImageUrl());
    }
}
