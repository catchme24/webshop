package com.example.service;

import com.example.dto.ProductCreateEditDto;
import com.example.dto.ProductReadDto;
import com.example.entity.Product;
import com.example.mapper.old.ProductCreateEditMapper2;
import com.example.mapper.old.ProductReadMapper2;
import com.example.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductReadMapper2 productReadMapper;
    private final ProductCreateEditMapper2 productCreateEditMapper;

    public ProductReadDto create(ProductCreateEditDto dto)throws Exception{
        Product product = productCreateEditMapper.map(dto);
        if (product.getProductName().equals("") || (product.getPrice().doubleValue() <= 0.0) || product.getDescription().
                equals("") || product.getImageUrl().equals("")) throw new Exception("");
        return productReadMapper.map(productRepository.save(product));
    }

    public ProductReadDto read(Integer id){
        return productReadMapper.map(productRepository.findById(id).get());
    }

    public ProductReadDto update(ProductCreateEditDto dto, Integer id){
        Product product = productRepository.findById(id).get();
        return productReadMapper.map(productCreateEditMapper.map(dto, product));
    }

    public void delete(Integer id){
        productRepository.delete(productRepository.findById(id).get());
    }

    public List<ProductReadDto> getAll() {
        return productRepository.findAll()
                .stream()
                .map(entity ->
                    productReadMapper.map(entity)
                )
                .collect(Collectors.toList());
    }
}
