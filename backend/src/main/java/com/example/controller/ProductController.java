package com.example.controller;

import com.example.controller.util.ControllerUtils;
import com.example.dto.order.ProductDto;
import com.example.service.ProductService;
import com.example.service.response.ServiceResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@RequestMapping("/api/products")
@RestController
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

//    @GetMapping
//    public ResponseEntity<?> readAll() {
//        ServiceResponse<ProductDto> sr = productService.readAll(null);
//        return ControllerUtils.mapServiceResponseToHttpResponse(sr);
//    }

    @GetMapping
    public ResponseEntity<?> readAll(@RequestBody(required = false) Collection<Long> ids) {
        ServiceResponse<ProductDto> sr = Objects.isNull(ids) ?
                productService.readAll(null) :
                productService.readAll(ids, null);
        return ControllerUtils.mapServiceResponseToHttpResponse(sr);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable("id") Long id) {
        ServiceResponse<ProductDto> sr = productService.read(id, null);
        return ControllerUtils.mapServiceResponseToHttpResponse(sr);
    }



    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProductDto dto) {
        ServiceResponse<ProductDto> sr = productService.create(dto, null);
        return ControllerUtils.mapServiceResponseToHttpResponse(sr);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        ServiceResponse<ProductDto> sr = productService.delete(id.longValue(), null);
        return ControllerUtils.mapServiceResponseToHttpResponse(sr);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody ProductDto dto,
                                    @PathVariable Integer id) {
        ServiceResponse<ProductDto> sr = productService.update(dto, null);
        return ControllerUtils.mapServiceResponseToHttpResponse(sr);
    }
}
