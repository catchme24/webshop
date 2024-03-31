package com.example.controller;

import com.example.dto.OrderReadDto;
import com.example.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/customers")
@RestController
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

//    @PostMapping
//    public ResponseEntity<CustomerReadDto> create(@RequestBody CustomerCreateEditDto dto){
//        CustomerReadDto customerReadDto = customerService.create(dto);
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .build();
//    }

    @GetMapping("/{id}")
    public ResponseEntity<List<OrderReadDto>> getAllOrders(@PathVariable("id") Integer customerId){

        List<OrderReadDto> list = customerService.getAllOrders(customerId);
        return ResponseEntity
                .ok()
                .body(list);
    }
//    @GetMapping("/{id}")
//    public ResponseEntity<CustomerReadDto> read(@PathVariable Integer id){
//        return new ResponseEntity<>(customerService.read(id), HttpStatus.OK);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<CustomerReadDto> update(@RequestBody CustomerCreateEditDto dto,@PathVariable Integer id){
//        return new ResponseEntity<>(customerService.update(dto, id), HttpStatus.ACCEPTED);
//    }
//
//    @DeleteMapping("/{id}")
//    public HttpStatus delete(@PathVariable Integer id){
//        customerService.delete(id);
//        return HttpStatus.I_AM_A_TEAPOT;
//    }

}
