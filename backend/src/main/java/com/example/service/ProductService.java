package com.example.service;

import com.example.dto.order.ProductDto;
import com.example.mapper.ProductMapper;
import com.example.repository.ProductRepository;
import com.example.service.response.ServiceResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ProductService implements ApiService<ProductDto> {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;


    @Override
    public ServiceResponse<ProductDto> readAll(UserDetails principal) {
        List<ProductDto> products = productRepository.findAll().stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
        return goodResponse(null, products);
    }

    public ServiceResponse<ProductDto> readAll(Collection<Long> ids, UserDetails principal) {
        List<ProductDto> products = productRepository.findAllById(ids
                        .stream()
                        .mapToInt(x -> x.intValue())
                        .boxed()
                        .collect(Collectors.toList()))
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
        return goodResponse(null, products);
    }

    @Override
    public ServiceResponse<ProductDto> read(Long id, UserDetails principal) {
        return goodResponse(null,
                                productRepository.findById(id.intValue())
                                        .map(productMapper::toDto)
                                        .orElse(null)
        );
    }

    @Override
    @Transactional
    public ServiceResponse<ProductDto> create(ProductDto dto, UserDetails principal) {
        ProductDto product = productMapper.toDto(productRepository.save(productMapper.toEntity(dto)));
        return goodResponse(null, product);
    }

    @Override
    @Transactional
    public ServiceResponse<ProductDto> update(ProductDto dto, UserDetails principal) {
        ProductDto product = productMapper.toDto(productRepository.save(productMapper.toEntity(dto)));
        return goodResponse(null, product);
    }

    @Override
    @Transactional
    public ServiceResponse<ProductDto> delete(Long id, UserDetails principal) {
        productRepository.deleteById(id.intValue());
        return goodResponse(null, new ArrayList<>());
    }
}
