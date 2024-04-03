package com.example.service;

import com.example.dto.ProductDto;
import com.example.entity.Product;
import com.example.mapper.ProductMapper;
import com.example.repository.ProductRepository;
import com.example.service.response.ServiceMessage;
import com.example.service.response.ServiceResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProductService implements ApiService<ProductDto> {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;


    @Override
    public ServiceResponse<ProductDto> readAll(UserDetails principal) {
        log.debug("Start finding all products");
        List<ProductDto> findedProducts = productRepository.findAll().stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
        log.debug("End finging all products");
        return goodResponse(HttpStatus.OK, findedProducts);
    }

    public ServiceResponse<ProductDto> readAll(Collection<Long> ids, UserDetails principal) {
        log.debug("Start finding all products by ids");
        List<ProductDto> findedProducts = productRepository.findAllById(ids
                        .stream()
                        .mapToInt(x -> x.intValue())
                        .boxed()
                        .collect(Collectors.toList()))
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
        log.debug("End finging all products by ids");
        return goodResponse(HttpStatus.OK, findedProducts);
    }

    @Override
    public ServiceResponse<ProductDto> read(Long id, UserDetails principal) {
        log.debug("Start finding product with id={}", id);
        Optional<Product> findedProduct  = productRepository.findById(id.intValue());
        if (findedProduct.isEmpty()) {
            log.warn("Сannot find product with id={}", id);
            return errorResponse(HttpStatus.NOT_FOUND, ServiceMessage.SHOULD_HAS_EXISTING_ID.name());
        }
        ProductDto productDto = productMapper.toDto(findedProduct.get());
        log.debug("End finging product with id={}", id);
        return goodResponse(HttpStatus.OK, productDto);
    }

    @Override
    @Transactional
    public ServiceResponse<ProductDto> create(ProductDto productToBeAdded, UserDetails principal) {
        log.debug("Start adding new product = {}", productToBeAdded);
        if (productToBeAdded.getProductId() != null) {
            log.warn("Сannot add new product: {}, new product should not has id", productToBeAdded);
            return errorResponse(HttpStatus.BAD_REQUEST, ServiceMessage.SHOULD_NOT_HAS_ID.name());
        }
        ProductDto addedProduct = productMapper.toDto(productRepository.save(productMapper.toEntity(productToBeAdded)));
        log.debug("End adding new product: {}", addedProduct);
        return goodResponse(HttpStatus.CREATED, addedProduct);
    }

    @Override
    @Transactional
    public ServiceResponse<ProductDto> update(ProductDto productToBeUpdated, UserDetails principal) {
        log.debug("Start updating product with id={}", productToBeUpdated.getProductId());
        ProductDto updatedProduct;
        if (productToBeUpdated.getProductId() == null) {
            log.warn("Сannot update server with id: {}, updated server should has existing id", productToBeUpdated.getProductId());
            return errorResponse(HttpStatus.BAD_REQUEST, ServiceMessage.SHOULD_HAS_EXISTING_ID.name());
        }
        Optional<Product> existingProduct = productRepository.findById(productToBeUpdated.getProductId());
        if (existingProduct.isEmpty()) {
            log.warn("Сannot update server with id: {}, updated server should has existing id", productToBeUpdated.getProductId());
            return errorResponse(HttpStatus.NOT_FOUND, ServiceMessage.SHOULD_HAS_EXISTING_ID.name());
        }
        updatedProduct = productMapper.toDto(productRepository.save(existingProduct.get()));
        log.debug("End updating server with id={}", productToBeUpdated.getProductId());
        return goodResponse(HttpStatus.ACCEPTED, updatedProduct);
    }

    @Override
    @Transactional
    public ServiceResponse<ProductDto> delete(Long id, UserDetails principal) {
        log.debug("Start deleting product with id={}", id);
        if (id == null) {
            log.warn("Сannot delete product with id: {}, deleted product should has existing id", id);
            return errorResponse(HttpStatus.BAD_REQUEST, ServiceMessage.SHOULD_HAS_EXISTING_ID.name());
        }
        Optional<Product> existingProduct = productRepository.findById(id.intValue());
        if (existingProduct.isEmpty()) {
            log.warn("Сannot delete product with id: {}, deleted product should has existing id", id);
            return errorResponse(HttpStatus.NOT_FOUND, ServiceMessage.SHOULD_HAS_EXISTING_ID.name());
        }
        productRepository.deleteById(id.intValue());
        log.debug("End deleting product with id={}", id);
        return goodResponse(HttpStatus.OK, new ArrayList<>());
    }
}
