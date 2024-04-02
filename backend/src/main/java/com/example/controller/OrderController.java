package com.example.controller;

import com.example.dto.ProductQuantityDto;
import com.example.dto.order.DeliveryListDto;
import com.example.dto.order.OrderDto;
import com.example.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/orders")
@RestController
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getById(@PathVariable("id") Integer orderId) {
        OrderDto orderDto = orderService.getById(orderId);
        return ResponseEntity
                .ok()
                .body(orderDto);
    }

    @PostMapping("/{id}")
    public ResponseEntity<OrderDto> addProductInOrder(@PathVariable("id") Integer orderId,
                                                      @RequestBody ProductQuantityDto dto) throws Exception {

        OrderDto orderDto = orderService.addProductInOrder(orderId, dto);
        return ResponseEntity
                .ok()
                .body(orderDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> changeCountOfProduct(@PathVariable("id") Integer orderId,
                                                         @RequestBody ProductQuantityDto dto){

        OrderDto orderReadDto = orderService.changeCountOfProduct(orderId, dto);
        return ResponseEntity
                .ok()
                .body(orderReadDto);
    }

    @DeleteMapping("/{orderId}/product/{productId}")
    public ResponseEntity<?> deleteProductFromOrder(@PathVariable("orderId") Integer orderId,
                                                    @PathVariable("productId") Integer productId){
        orderService.deleteProductFromOrder(orderId, productId);
        return ResponseEntity
                .ok()
                .build();
    }

    @PostMapping("/{id}/form-order")
    public ResponseEntity<OrderDto> addDeliveryList(@RequestBody DeliveryListDto dto,
                                                    @PathVariable("id") Integer orderId){

        OrderDto orderDto = orderService.addDeliveryList(orderId, dto);
        return ResponseEntity
                .ok()
                .body(orderDto);
    }

}
