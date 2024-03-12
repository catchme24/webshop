package com.example.mapper;

import com.example.dto.OrderProductReadDto;
import com.example.entity.OrderProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderProductReadMapper implements Mapper<OrderProduct, OrderProductReadDto> {

    @Override
    public OrderProductReadDto map(OrderProduct object) {
        return new OrderProductReadDto(
                object.getOrderProductId(),
                object.getQuantity());
    }
}
