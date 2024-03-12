package com.example.controller;

import com.example.dto.ProductCreateEditDto;
import com.example.dto.ProductReadDto;
import com.example.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/products")
@RestController
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductReadDto> create(@RequestBody ProductCreateEditDto dto) throws Exception{
        return new ResponseEntity<>(productService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductReadDto>> getAll(){
        List<ProductReadDto> list = productService.getAll();
        return ResponseEntity
                .ok()
                .body(list);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductReadDto> delete(@PathVariable Integer id){
        productService.delete(id);
        return ResponseEntity
                .ok()
                .build();
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<ProductReadDto> update(@RequestBody ProductCreateEditDto dto, @PathVariable Integer id){
//        return new ResponseEntity<>(productService.update(dto, id), HttpStatus.ACCEPTED);
//    }
}
