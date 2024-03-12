package com.example.controller;

import com.example.dto.DeliveryListCreateEditDto;
import com.example.dto.OrderReadDto;
import com.example.dto.ProductQuantityCreateEditDto;
import com.example.service.DeliveryListService;
import com.example.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/orders")
@RestController
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final DeliveryListService deliveryListService;

    @PostMapping("/{id}")
    public ResponseEntity<OrderReadDto> addProductInOrder(@PathVariable("id") Integer orderId,
                                                          @RequestBody ProductQuantityCreateEditDto dto) throws Exception {

        OrderReadDto orderReadDto = orderService.addProductInOrder(orderId, dto);

        return ResponseEntity
                .ok()
                .body(orderReadDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderReadDto> changeCountOfProduct(@PathVariable("id") Integer orderId,
                                                             @RequestBody ProductQuantityCreateEditDto dto){

        OrderReadDto orderReadDto = orderService.changeCountOfProduct(orderId, dto);

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
    public ResponseEntity<OrderReadDto> addDeliveryList(@RequestBody DeliveryListCreateEditDto dto,
                                                             @PathVariable("id") Integer orderId){

        OrderReadDto orderReadDto = orderService.addDeliveryList(orderId, dto);

        return ResponseEntity
                .ok()
                .body(orderReadDto);
    }

//    @GetMapping ("/{id}/customer")
//    public ResponseEntity<List<OrderReadDto>> getAllOrders(@PathVariable("id") Integer customerId){
//
//
//        List<OrderReadDto> list = customerService.getAllOrders(customerId);
//        return ResponseEntity
//                .ok()
//                .body(list);
//    }

//    @PostMapping
//    public ResponseEntity<OrderReadDto> create(@RequestBody OrderCreateEditDto dto){
//        return new ResponseEntity<>(orderService.create(dto), HttpStatus.CREATED);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<OrderReadDto> read(@RequestBody Integer id){
//        return new ResponseEntity<>(orderService.read(id), HttpStatus.OK);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<OrderReadDto> update(@RequestBody OrderCreateEditDto dto, Integer id){
//        return new ResponseEntity<>(orderService.update(dto, id), HttpStatus.ACCEPTED);
//    }
//

}
