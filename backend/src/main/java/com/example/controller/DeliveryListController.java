package com.example.controller;

import com.example.dto.DeliveryListCreateEditDto;
import com.example.dto.DeliveryListReadDto;
import com.example.service.DeliveryListService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/deliveryList")
@RestController
@AllArgsConstructor
public class DeliveryListController {

    private final DeliveryListService deliveryListService;

    @PostMapping
    public ResponseEntity<DeliveryListReadDto> create(@RequestBody DeliveryListCreateEditDto dto){
        return new ResponseEntity<>(deliveryListService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryListReadDto> read(@PathVariable Integer id){
        return new ResponseEntity<>(deliveryListService.read(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeliveryListReadDto> update(@RequestBody DeliveryListCreateEditDto dto,@PathVariable Integer id){
        return new ResponseEntity<>(deliveryListService.update(dto, id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Integer id){
        deliveryListService.delete(id);
        return HttpStatus.I_AM_A_TEAPOT;
    }
}
