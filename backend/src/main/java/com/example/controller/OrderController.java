package com.example.controller;

import com.example.controller.util.ControllerUtils;
import com.example.dto.ProductQuantityDto;
import com.example.dto.order.OrderDto;
import com.example.service.OrderService;
import com.example.service.response.ServiceResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/orders")
@RestController
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> readAll(@PathVariable("id") Integer orderId,
                                    @AuthenticationPrincipal UserDetails userDetails) {

        ServiceResponse<OrderDto> sr = orderService.readAll(orderId, userDetails);
        return ControllerUtils.mapServiceResponseToHttpResponse(sr);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/{id}")
    public ResponseEntity<?> addProductInOrder(@PathVariable("id") Integer orderId,
                                               @RequestBody ProductQuantityDto dto,
                                               @AuthenticationPrincipal UserDetails userDetails) {

        ServiceResponse<OrderDto> sr = orderService.addProductInOrder(orderId, dto, userDetails);
        return ControllerUtils.mapServiceResponseToHttpResponse(sr);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> changeCountOfProduct(@PathVariable("id") Integer orderId,
                                                  @RequestBody ProductQuantityDto dto,
                                                  @AuthenticationPrincipal UserDetails userDetails){

        ServiceResponse<OrderDto> sr = orderService.changeCountOfProduct(orderId, dto, userDetails);
        return ControllerUtils.mapServiceResponseToHttpResponse(sr);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/form-current-order")
    public ResponseEntity<?> formCurrentOrder(@RequestBody String paymentMethod,
                                             @PathVariable("id") Integer orderId,
                                             @AuthenticationPrincipal UserDetails userDetails){

        ServiceResponse<OrderDto> sr = orderService.formCurrentOrder(orderId, paymentMethod, userDetails);
        return ControllerUtils.mapServiceResponseToHttpResponse(sr);
    }

}
